package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.IFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.IHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor.CommentDialogInteractor
import com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor.ICommentDialogInteractor
import com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor.CurrentFortuneDescriptionInteractor
import com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor.ICurrentFortuneDescriptionInteractor
import com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor.CurrentFortuneInteractor
import com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor.ICurrentFortuneInteractor
import com.magicrunes.magicrunes.domain.interactors.fortuneinteractor.FortuneInteractor
import com.magicrunes.magicrunes.domain.interactors.fortuneinteractor.IFortuneInteractor
import com.magicrunes.magicrunes.domain.interactors.historyInteractor.HistoryInteractor
import com.magicrunes.magicrunes.domain.interactors.historyInteractor.IHistoryInteractor
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.IRuneInfoInteractor
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.RuneInfoInteractor
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.RuneOfTheDayInteractor
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.FortuneFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Singleton
    @Provides
    fun runeOfTheDayInteractor(
        runeRepository: IRuneRepository,
        historyRuneRepository: IHistoryRuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository
    ): IRuneOfTheDayInteractor = RuneOfTheDayInteractor(runeRepository, historyRuneRepository, runeDescriptionRepository)

    @Singleton
    @Provides
    fun runeInfoInteractor(
        runeRepository: IRuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository,
        imageService: ImageService
    ): IRuneInfoInteractor = RuneInfoInteractor(runeRepository, runeDescriptionRepository, imageService)

    @Singleton
    @Provides
    fun historyInteractor(
        runeRepository: IRuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository,
        historyRuneRepository: IHistoryRuneRepository,
        fortuneRepository: IFortuneRepository,
        historyFortuneRepository: IHistoryFortuneRepository
    ): IHistoryInteractor = HistoryInteractor(runeRepository, runeDescriptionRepository, historyRuneRepository, fortuneRepository, historyFortuneRepository)

    @Singleton
    @Provides
    fun fortuneInteractor(
        fortuneRepository: IFortuneRepository,
        fortuneFactory: FortuneFactory
    ): IFortuneInteractor = FortuneInteractor(fortuneRepository, fortuneFactory)

    @Singleton
    @Provides
    fun currentFortuneInteractor(
        runeRepository: IRuneRepository,
        historyFortuneRepository: IHistoryFortuneRepository,
        fortuneRepository: IFortuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository,
        fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository
    ): ICurrentFortuneInteractor = CurrentFortuneInteractor(runeRepository, historyFortuneRepository, fortuneRepository, runeDescriptionRepository, fortuneHistoryRunesRepository)

    @Singleton
    @Provides
    fun currentFortuneDescriptionInteractor(
        fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository,
        historyFortuneRepository: IHistoryFortuneRepository,
        runeRepository: IRuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository
    ): ICurrentFortuneDescriptionInteractor = CurrentFortuneDescriptionInteractor(fortuneHistoryRunesRepository, historyFortuneRepository, runeRepository, runeDescriptionRepository)

    @Singleton
    @Provides
    fun commentDialogInteractor(
        runeRepository: IRuneRepository,
        historyRuneRepository: IHistoryRuneRepository
    ): ICommentDialogInteractor = CommentDialogInteractor(runeRepository, historyRuneRepository)
}