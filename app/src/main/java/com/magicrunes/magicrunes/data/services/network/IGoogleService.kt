package com.magicrunes.magicrunes.data.services.network

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow

interface IGoogleService {
    fun getLastSignedInAccount(): GoogleSignInAccount?

    fun setSignedInAccount(account: GoogleSignInAccount)

    suspend fun firestoreSignIn(account: GoogleSignInAccount, needUpdateUi: Boolean = false)

    fun getGoogleSignInClient(): GoogleSignInClient

    suspend fun googleSignIn()

    suspend fun googleSignOut(): Task<Void>

    fun isAccountExpired(): Boolean

    fun getFirebaseUid(): String?

    fun getGooglePhoto(): String?

    val googleStateFlow: MutableStateFlow<Boolean>
}