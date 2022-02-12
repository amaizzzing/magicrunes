package com.magicrunes.magicrunes.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor.ICommentDialogInteractor
import com.magicrunes.magicrunes.ui.models.CommentDialogModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val HISTORY_DATE = "HISTORY_DATE"

class AddCommentDialogFragment: DialogFragment(), CoroutineScope{
    override val coroutineContext: CoroutineContext =
        Dispatchers.IO + SupervisorJob()

    @Inject
    lateinit var commentDialogInteractor: ICommentDialogInteractor

    private var currentCommentModel: CommentDialogModel? = null

    private var historyDate: Long? = null

    interface OnCloseClick {
        fun onCloseAddCommentDialog(newComment: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        historyDate = arguments?.getLong(HISTORY_DATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_to_the_rune, container, false)

        val cancelButton = view.findViewById<TextView>(R.id.cancelButton)
        cancelButton.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
        }

        val textRune = view.findViewById<TextView>(R.id.textRune)
        val textDate = view.findViewById<TextView>(R.id.textDate)
        val commentText = view.findViewById<EditText>(R.id.editComment)

        val okButton = view.findViewById<TextView>(R.id.okButton)
        okButton.setOnClickListener {
            launch(coroutineContext) {
                currentCommentModel?.let { model ->
                    if (model.comment != commentText.text.toString()){
                        commentDialogInteractor.saveComment(
                            model.dateInMillis,
                            commentText.text.toString()
                        )
                        model.comment = commentText.text.toString()
                    }
                }

                parentFragmentManager
                    .beginTransaction()
                    .remove(this@AddCommentDialogFragment)
                    .commit()
            }
        }

        commentText.addTextChangedListener {
            okButton.text =
                requireContext().getString(
                    if (it.contentEquals(currentCommentModel?.comment)) {
                        R.string.dialog_to_the_rune_OK
                    } else {
                        R.string.dialog_to_the_rune_change
                    }
                )
        }

        launch(coroutineContext) {
            commentDialogInteractor.getDialogComment(historyDate)?.let { dialogCommentModel ->
                currentCommentModel = dialogCommentModel

                withContext(Dispatchers.Main) {
                    textRune.text = dialogCommentModel.runeName
                    textDate.text = dialogCommentModel.date
                    commentText.setText(dialogCommentModel.comment)
                    commentText.setSelection(commentText.text.length)
                }
            }
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        return dialog
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog?.window?.apply {
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawableResource(R.drawable.round_corners)
        }
    }

    override fun onDestroy() {
        onCloseImpl?.onCloseAddCommentDialog(currentCommentModel?.comment ?: "")
        super.onDestroy()

        coroutineContext.cancel()
    }

    companion object {
        fun newInstance(idHistory: Long? = null): AddCommentDialogFragment =
            AddCommentDialogFragment().apply {
                idHistory?.let {
                    if (idHistory != -1L) {
                        this.arguments = bundleOf(HISTORY_DATE to it)
                    }
                }
            }

        var onCloseImpl: OnCloseClick? = null
    }
}