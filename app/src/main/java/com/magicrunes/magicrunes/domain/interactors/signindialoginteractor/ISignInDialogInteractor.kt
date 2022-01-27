package com.magicrunes.magicrunes.domain.interactors.signindialoginteractor

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface ISignInDialogInteractor {
    suspend fun writeGoogleUserToCache(
        idGoogle: String,
        idFirestore: String,
        name: String
    )

    suspend fun syncLocaleAndFirestoreLastRune()
}