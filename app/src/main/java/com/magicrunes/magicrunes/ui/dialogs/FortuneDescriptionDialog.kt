package com.magicrunes.magicrunes.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.GlideImageViewLoader
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.domain.interactors.fortuneinteractor.IFortuneInteractor
import com.magicrunes.magicrunes.utils.setInvisible
import com.magicrunes.magicrunes.utils.setVisible
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val FORTUNE_ID = "FORTUNE_ID"
const val FORTUNE_DESCRIPTION_DIALOG_TAG = "AddCommentDialogFragment"

class FortuneDescriptionDialog: DialogFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    @Inject
    lateinit var imageViewLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    @Inject
    lateinit var fortuneInteractor: IFortuneInteractor

    private var fortuneId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        fortuneId = arguments?.getLong(FORTUNE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fortune_description_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<ConstraintLayout>(R.id.description_container)
        val pb = view.findViewById<ProgressBar>(R.id.pb_fortune_description)
        container.setInvisible()
        pb.setVisible()

        launch(Dispatchers.IO) {
            fortuneId?.let { id ->
                val fortune = fortuneInteractor.getFortuneById(id)
                fortune?.let {
                    withContext(Dispatchers.Main) {
                        container.setVisible()
                        pb.setInvisible()
                        val overviewTitle = view.findViewById<TextView>(R.id.overview_title)
                        val imagePosterDescription = view.findViewById<ImageView>(R.id.image_poster_description)
                        val titleDescription = view.findViewById<TextView>(R.id.title_description)
                        val timeLastFortune = view.findViewById<TextView>(R.id.time_last_fortune)
                        val countRunes = view.findViewById<TextView>(R.id.count_runes)
                        overviewTitle.text = it.description
                        titleDescription.text = it.nameFortune
                        timeLastFortune.text = it.date
                        countRunes.text = it.currentStrategy?.visibleRuneList?.size.toString()
                        imageViewLoader.loadInto(
                            imageService.getImageResource(it.image),
                            imagePosterDescription,
                            24
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()
        dialog?.window?.apply {
            setLayout(width, height)
            setBackgroundDrawableResource(R.drawable.round_corners)
        }
    }

    companion object {
        fun newInstance(idFortune: Long? = null): FortuneDescriptionDialog =
            FortuneDescriptionDialog().apply {
                idFortune?.let {
                    this.arguments = bundleOf(FORTUNE_ID to it)
                }
            }
    }
}