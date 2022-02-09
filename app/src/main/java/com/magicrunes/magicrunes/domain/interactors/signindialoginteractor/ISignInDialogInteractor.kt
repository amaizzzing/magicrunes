package com.magicrunes.magicrunes.domain.interactors.signindialoginteractor

interface ISignInDialogInteractor {
    suspend fun writeGoogleUserToCache(
        idGoogle: String,
        idFirestore: String,
        name: String
    )

    suspend fun syncLocaleAndFirestoreLastRune()
}