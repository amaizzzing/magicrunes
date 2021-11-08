package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.repositories.fortune.FortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.IFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.IHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.RuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.RuneDescriptionRepository
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB
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
}