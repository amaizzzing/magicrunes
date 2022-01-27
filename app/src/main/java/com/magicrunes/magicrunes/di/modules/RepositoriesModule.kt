package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.repositories.fortune.FortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FirestoreFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesFactory
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.IFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.FirestoreHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.historyFortune.IHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.FirestoreHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.RuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.RuneDescriptionRepository
import com.magicrunes.magicrunes.data.repositories.userInfo.IUserInfoRepository
import com.magicrunes.magicrunes.data.repositories.userInfo.UserInfoRepository
import com.magicrunes.magicrunes.data.services.database.firestore.IFireDatabase
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB
import com.magicrunes.magicrunes.data.services.network.IGoogleService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun runeRepository(db: MagicRunesDB): IRuneRepository = RuneRepository(db)

    @Singleton
    @Provides
    fun historyRuneRepository(db: MagicRunesDB): IHistoryRuneRepository = HistoryRuneRepository(db)

    @Singleton
    @Provides
    fun firestoreHistoryRuneRepository(db: IFireDatabase, localDB: MagicRunesDB): FirestoreHistoryRuneRepository =
        FirestoreHistoryRuneRepository(db, localDB)

    @Singleton
    @Provides
    fun firestoreHistoryFortuneRepository(db: IFireDatabase, localDB: MagicRunesDB): FirestoreHistoryFortuneRepository =
        FirestoreHistoryFortuneRepository(db, localDB)

    @Singleton
    @Provides
    fun firestoreFortuneHistoryRunesRepository(db: IFireDatabase, localDB: MagicRunesDB): FirestoreFortuneHistoryRunesRepository =
        FirestoreFortuneHistoryRunesRepository(db, localDB)

    @Singleton
    @Provides
    fun runeDescriptionRepository(db: MagicRunesDB): IRuneDescriptionRepository = RuneDescriptionRepository(db)

    @Singleton
    @Provides
    fun fortuneRepository(db: MagicRunesDB): IFortuneRepository = FortuneRepository(db)

    @Singleton
    @Provides
    fun historyFortuneRepository(db: MagicRunesDB): IHistoryFortuneRepository = HistoryFortuneRepository(db)

    @Singleton
    @Provides
    fun fortuneHistoryRunesRepository(db: MagicRunesDB): IFortuneHistoryRunesRepository = FortuneHistoryRunesRepository(db)

    @Singleton
    @Provides
    fun userInfoRepository(db: MagicRunesDB): IUserInfoRepository = UserInfoRepository(db)

    @Provides
    @Singleton
    fun historyRuneRepositoryFactory(
        googleService: IGoogleService,
        historyRuneRepository: IHistoryRuneRepository,
        firestoreHistoryRuneRepository: FirestoreHistoryRuneRepository
    ): HistoryRuneRepositoryFactory =
        HistoryRuneRepositoryFactory(googleService,  historyRuneRepository, firestoreHistoryRuneRepository)

    @Provides
    @Singleton
    fun historyFortuneRepositoryFactory(
        googleService: IGoogleService,
        historyFortuneRepository: IHistoryFortuneRepository,
        firestoreHistoryFortuneRepository: FirestoreHistoryFortuneRepository
    ): HistoryFortuneRepositoryFactory =
        HistoryFortuneRepositoryFactory(googleService,  historyFortuneRepository, firestoreHistoryFortuneRepository)

    @Provides
    @Singleton
    fun fortuneHistoryRunesFactory(
        googleService: IGoogleService,
        fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository,
        firestoreFortuneHistoryRunesRepository: FirestoreFortuneHistoryRunesRepository
    ): FortuneHistoryRunesFactory =
        FortuneHistoryRunesFactory(googleService,  fortuneHistoryRunesRepository, firestoreFortuneHistoryRunesRepository)
}