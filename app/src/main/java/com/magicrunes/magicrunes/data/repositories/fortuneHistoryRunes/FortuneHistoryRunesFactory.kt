package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.services.network.IGoogleService

class FortuneHistoryRunesFactory (
    private val googleService: IGoogleService,
    private val fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository,
    private val firestoreFortuneHistoryRunesRepository: FirestoreFortuneHistoryRunesRepository
) {
    fun getFortuneRepository(): IFortuneHistoryRunesRepository =
        if (googleService.getLastSignedInAccount() == null/* || googleService.isAccountExpired()*/) {
            fortuneHistoryRunesRepository
        } else {
            firestoreFortuneHistoryRunesRepository
        }
}