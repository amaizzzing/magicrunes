package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesFactory
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.historyRune.FirestoreHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.data.repositories.userInfo.IUserInfoRepository
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
import com.magicrunes.magicrunes.domain.interactors.notificationinteractor.INotificationInteractor
import com.magicrunes.magicrunes.domain.interactors.notificationinteractor.NotificationInteractor
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.IRuneInfoInteractor
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.RuneInfoInteractor
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.RuneOfTheDayInteractor
import com.magicrunes.magicrunes.domain.interactors.signindialoginteractor.ISignInDialogInteractor
import com.magicrunes.magicrunes.domain.interactors.signindialoginteractor.SignInDialogInteractor
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
        historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
        runeDescriptionRepository: IRuneDescriptionRepository
    ): IRuneOfTheDayInteractor = RuneOfTheDayInteractor(runeRepository, historyRuneRepositoryFactory, runeDescriptionRepository)

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
        historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
        fortuneRepository: IFortuneRepository,
        historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory
    ): IHistoryInteractor = HistoryInteractor(runeRepository, runeDescriptionRepository, historyRuneRepositoryFactory, fortuneRepository, historyFortuneRepositoryFactory)

    @Singleton
    @Provides
    fun fortuneInteractor(
        fortuneRepository: IFortuneRepository,
        historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
        fortuneFactory: FortuneFactory
    ): IFortuneInteractor = FortuneInteractor(fortuneRepository, historyFortuneRepositoryFactory, fortuneFactory)

    @Singleton
    @Provides
    fun currentFortuneInteractor(
        runeRepository: IRuneRepository,
        historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
        fortuneRepository: IFortuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository,
        fortuneHistoryRunesFactory: FortuneHistoryRunesFactory
    ): ICurrentFortuneInteractor = CurrentFortuneInteractor(runeRepository, historyFortuneRepositoryFactory, fortuneRepository, runeDescriptionRepository, fortuneHistoryRunesFactory)

    @Singleton
    @Provides
    fun currentFortuneDescriptionInteractor(
        fortuneHistoryRunesFactory: FortuneHistoryRunesFactory,
        historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
        runeRepository: IRuneRepository,
        runeDescriptionRepository: IRuneDescriptionRepository
    ): ICurrentFortuneDescriptionInteractor = CurrentFortuneDescriptionInteractor(fortuneHistoryRunesFactory, historyFortuneRepositoryFactory, runeRepository, runeDescriptionRepository)

    @Singleton
    @Provides
    fun commentDialogInteractor(
        runeRepository: IRuneRepository,
        historyRuneRepositoryFactory: HistoryRuneRepositoryFactory
    ): ICommentDialogInteractor = CommentDialogInteractor(runeRepository, historyRuneRepositoryFactory)

    @Singleton
    @Provides
    fun signInDialogInteractor(
        userInfoRepository: IUserInfoRepository,
        historyRuneRepository: IHistoryRuneRepository,
        firestoreHistoryRuneRepository: FirestoreHistoryRuneRepository
    ): ISignInDialogInteractor = SignInDialogInteractor(userInfoRepository, historyRuneRepository, firestoreHistoryRuneRepository)

    @Singleton
    @Provides
    fun notificationInteractor(
        historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
        fortuneRepository: IFortuneRepository
    ): INotificationInteractor = NotificationInteractor(historyRuneRepositoryFactory, fortuneRepository)
}