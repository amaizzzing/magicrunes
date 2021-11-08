package com.magicrunes.magicrunes.di.modules

import androidx.lifecycle.ViewModel
import com.magicrunes.magicrunes.di.annotation.ViewModelKey
import com.magicrunes.magicrunes.ui.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainViewModel(mainActivityViewModel: MainFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FortuneFragmentViewModel::class)
    abstract fun bindFortuneViewModel(fortuneFragmentViewModel: FortuneFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryFragmentViewModel::class)
    abstract fun bindHistoryViewModel(historyFragmentViewModel: HistoryFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoFragmentViewModel::class)
    abstract fun bindInfoViewModel(infoFragmentViewModel: InfoFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentFortuneViewModel::class)
    abstract fun bindCurrentFortuneModel(currentFortuneViewModel: CurrentFortuneViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentFortuneDescriptionViewModel::class)
    abstract fun bindCurrentFortuneDescriptionModel(currentFortuneDescriptionViewModel: CurrentFortuneDescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoDescriptionViewModel::class)
    abstract fun bindInfoDescriptionModel(infoDescriptionViewModel: InfoDescriptionViewModel): ViewModel
}