package com.magicrunes.magicrunes.data.services.network

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

interface IGoogleService {
    fun getLastSignedInAccount(): GoogleSignInAccount?

    fun setSignedInAccount(account: GoogleSignInAccount)

    fun getGoogleSignInClient(): GoogleSignInClient

    fun googleSignIn()

    fun googleSignOut(): Task<Void>
}