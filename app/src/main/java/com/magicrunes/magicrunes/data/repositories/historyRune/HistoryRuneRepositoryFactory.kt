package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.services.network.IGoogleService

class HistoryRuneRepositoryFactory(
    private val googleService: IGoogleService,
    private val historyRuneRepository: IHistoryRuneRepository,
    private val firestoreHistoryRuneRepository: FirestoreHistoryRuneRepository
) {
    fun getHistoryRuneRepository(): IHistoryRuneRepository =
        if (googleService.getLastSignedInAccount() == null/* || googleService.isAccountExpired()*/) {
            historyRuneRepository
        } else {
            firestoreHistoryRuneRepository
        }
}