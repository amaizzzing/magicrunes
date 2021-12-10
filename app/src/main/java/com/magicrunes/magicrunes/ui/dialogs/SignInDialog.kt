package com.magicrunes.magicrunes.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.network.IGoogleService
import javax.inject.Inject

class SignInDialog: DialogFragment() {
    interface OnGoogleSign {
        fun onGoogleSignIn()
        fun onGoogleSignOut()
    }

    @Inject
    lateinit var imageViewLoader: IImageLoader<ImageView>

    @Inject
    lateinit var googleService: IGoogleService

    lateinit var signInButton: CardView
    lateinit var textSignInButton: TextView
    lateinit var textEmail: TextView
    lateinit var textDisplayName: TextView
    lateinit var accountAvatar: ImageView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)!!

                googleService.setSignedInAccount(account)

                showAccountData(account)
            } catch (e: ApiException) {
                Log.e("SignInDialog", e.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)
    }

    private fun showAccountData(googleAccount: GoogleSignInAccount?) {
        googleAccount.let { gAccount ->
            textSignInButton.text = "LogOut"
            imageViewLoader.loadInto(gAccount?.photoUrl.toString(), accountAvatar, circleCrop = true)
            textEmail.text = gAccount?.email
            textDisplayName.text = gAccount?.displayName
            onGoogleSignImpl?.onGoogleSignIn()
        } ?: run {
            textSignInButton.text = "Sign in with Google"
            textEmail.text = ""
            textDisplayName.text = ""
            onGoogleSignImpl?.onGoogleSignOut()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_sign_in, container, false)

        signInButton = view.findViewById(R.id.sign_in_button)
        textSignInButton = view.findViewById(R.id.text_sign_in_button)
        accountAvatar = view.findViewById(R.id.avatar_dialog_sign_in)
        textEmail = view.findViewById(R.id.email_dialog_sign_in)
        textDisplayName = view.findViewById(R.id.display_name_dialog_sign_in)

        val account = googleService.getLastSignedInAccount()

        showAccountData(account)

        signInButton.setOnClickListener {
            val googleAccount = googleService.getLastSignedInAccount()
            if (googleAccount == null) {
                val signInIntent: Intent = googleService.getGoogleSignInClient().signInIntent

                resultLauncher.launch(signInIntent)
            } else {
                googleService.googleSignOut().addOnCompleteListener {
                    textSignInButton.text = "Sign in with Google"
                    textEmail.text = ""
                    textDisplayName.text = ""
                    onGoogleSignImpl?.onGoogleSignOut()
                    imageViewLoader.loadInto(R.drawable.account_image_grey, accountAvatar)
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
        val height = (resources.displayMetrics.heightPixels * 0.6).toInt()
        dialog?.window?.apply {
            setLayout(width, height)
            setBackgroundDrawableResource(R.drawable.round_corners)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onGoogleSignImpl = null
    }

    companion object {
        fun newInstance(): SignInDialog = SignInDialog()

        var onGoogleSignImpl: OnGoogleSign? = null
    }
}