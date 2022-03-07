package com.magicrunes.magicrunes.domain.interactors.signindialoginteractor

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity
import com.magicrunes.magicrunes.data.repositories.historyRune.FirestoreHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.userInfo.IUserInfoRepository
import com.magicrunes.magicrunes.utils.DateUtils
import org.joda.time.DateTime

class SignInDialogInteractor(
    val userInfoRepository: IUserInfoRepository,
    val historyRuneRepository: IHistoryRuneRepository,
    val firestoreHistoryRuneRepository: FirestoreHistoryRuneRepository
): ISignInDialogInteractor {
    override suspend fun writeGoogleUserToCache(
        idGoogle: String,
        idFirestore: String,
        name: String
    ) {
        userInfoRepository.getUserInfo()?.let {
            userInfoRepository.updateUserInfo(UserInfoDbEntity().apply {
                this.idGoogle = idGoogle
                this.idFirestore = idFirestore
                this.name = name
            })
        } ?: userInfoRepository.insertUserInfo(UserInfoDbEntity().apply {
            this.idGoogle = idGoogle
            this.idFirestore = idFirestore
            this.name = name
        })
    }

    override suspend fun syncLocaleAndFirestoreLastRune() {
        val localLastRune = historyRuneRepository.getLastRune()
        val firestoreLastRune = firestoreHistoryRuneRepository.getLastRune()
        if (localLastRune != null && firestoreLastRune != null) {
            val isLocalRuneToday = DateUtils.isTheSameDay(DateTime(localLastRune.date), DateTime())
            val isFirestoreRuneToday = DateUtils.isTheSameDay(DateTime(firestoreLastRune.date), DateTime())
            when {
                isFirestoreRuneToday -> {
                    if (isLocalRuneToday) {
                        historyRuneRepository.updateHistoryRuneByDate(
                            firestoreLastRune.idRune,
                            firestoreLastRune.comment,
                            firestoreLastRune.state,
                            firestoreLastRune.syncState,
                            firestoreLastRune.isNotificationShow,
                            localLastRune.date
                        )
                    } else {
                        historyRuneRepository.insert(HistoryRuneDbEntity().apply {
                            idRune = firestoreLastRune.idRune
                            comment = firestoreLastRune.comment
                            state = firestoreLastRune.state
                            syncState = firestoreLastRune.syncState
                            date = firestoreLastRune.date
                        })
                    }
                }
                !isFirestoreRuneToday && isLocalRuneToday -> {
                    firestoreHistoryRuneRepository.updateHistoryRuneByDate(
                        localLastRune.idRune,
                        localLastRune.comment,
                        localLastRune.state,
                        localLastRune.syncState,
                        localLastRune.isNotificationShow,
                        firestoreLastRune.date
                    )
                }
            }
        }
    }
}