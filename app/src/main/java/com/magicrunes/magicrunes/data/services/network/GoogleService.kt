package com.magicrunes.magicrunes.data.services.network

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class GoogleService(
    private val context: Context
): IGoogleService, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = MagicRunesApp.backgroundTaskDispatcher + SupervisorJob()

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var firebaseAuth: FirebaseAuth

    private var googleAccount: GoogleSignInAccount? = null

    override val googleStateFlow = MutableStateFlow(false)
    private val _googleStateFlow: StateFlow<Boolean> = googleStateFlow

    override suspend fun googleSignIn() {
        val idToken = with(context.resources) {
            getString(getIdentifier("googleRequestIdToken", "string", context.packageName))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(idToken)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        googleAccount = getLastSignedInAccount()

        googleAccount?.let {
            firestoreSignIn(it)
            googleStateFlow.value = true

        } ?: run {
            googleStateFlow.value = false
        }
    }

    override fun isAccountExpired(): Boolean = googleAccount?.isExpired == true

    override fun getLastSignedInAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    override fun setSignedInAccount(account: GoogleSignInAccount) {
        googleAccount = account
    }

    override suspend fun firestoreSignIn(account: GoogleSignInAccount, needUpdateUi: Boolean) {
        account.idToken?.let {
            val cred = GoogleAuthProvider.getCredential(it, null)
            firebaseAuth = Firebase.auth
            firebaseAuth.signInWithCredential(cred).addOnSuccessListener {
                signInCallback?.invoke(
                    account.id ?: "",
                    getFirebaseUid() ?: "",
                    account.displayName ?: ""
                )
            }.addOnFailureListener {
                println("addOnFailureListener")
            }
        }
    }

    override fun getGoogleSignInClient(): GoogleSignInClient =
        mGoogleSignInClient

    override suspend fun googleSignOut(): Task<Void> {
        googleAccount = null
        googleStateFlow.value = false

        return mGoogleSignInClient.signOut()
    }

    override fun getFirebaseUid(): String? = firebaseAuth.uid

    override fun getGooglePhoto(): String? =
        if (googleAccount != null) {
            googleAccount?.photoUrl.toString()
        } else {
            null
        }

    companion object {
        var signInCallback: ((idGoogle: String, idFirestore: String, name: String) -> Unit)? = null
    }
}