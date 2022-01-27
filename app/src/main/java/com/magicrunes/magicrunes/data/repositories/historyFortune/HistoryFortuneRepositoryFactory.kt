package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.services.network.IGoogleService

class HistoryFortuneRepositoryFactory(
    private val googleService: IGoogleService,
    private val historyFortuneRepository: IHistoryFortuneRepository,
    private val firestoreHistoryFortuneRepository: FirestoreHistoryFortuneRepository
) {
    fun getFortuneRepository(): IHistoryFortuneRepository =
        if (googleService.getLastSignedInAccount() == null/* || googleService.isAccountExpired()*/) {
            historyFortuneRepository
        } else {
            firestoreHistoryFortuneRepository
        }
}