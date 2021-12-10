package com.magicrunes.magicrunes.data.services.network

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleService(
    private val context: Context
): IGoogleService {
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var firebaseAuth: FirebaseAuth

    private var googleAccount: GoogleSignInAccount? = null

    init {
        googleSignIn()
    }

    override fun googleSignIn() {
        val idToken = with(context.resources) {
            getString(getIdentifier("googleRequestIdToken", "string", context.packageName))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(idToken)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        firebaseAuth = Firebase.auth

        googleAccount = getLastSignedInAccount()
    }

    override fun getLastSignedInAccount(): GoogleSignInAccount? {
        googleAccount = GoogleSignIn.getLastSignedInAccount(context)

        return googleAccount
    }

    override fun setSignedInAccount(account: GoogleSignInAccount) {
        googleAccount = account
    }

    override fun getGoogleSignInClient(): GoogleSignInClient =
        mGoogleSignInClient

    override fun googleSignOut(): Task<Void> {
        googleAccount = null

        return mGoogleSignInClient.signOut()
    }
}