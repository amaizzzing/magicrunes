package com.magicrunes.magicrunes.di.components

import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.services.network.IGoogleService
import com.magicrunes.magicrunes.di.WorkerModule
import com.magicrunes.magicrunes.di.modules.*
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.adapters.FortuneFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.HistoryFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.InfoFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.fortune.FortuneListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.history.HistoryListPresenter
import com.magicrunes.magicrunes.ui.dialogs.AddCommentDialogFragment
import com.magicrunes.magicrunes.ui.dialogs.SignInDialog
import com.magicrunes.magicrunes.ui.fragments.*
import com.magicrunes.magicrunes.ui.widget.RuneOfTheDayWidget
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RepositoriesModule::class,
        ServicesModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        InteractorModule::class,
        AdaptersPresenterModule::class,
        AnimationHelperModule::class,
        WorkerModule::class
    ]
)
interface AppComponent: AndroidInjector<MagicRunesApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MagicRunesApp): Builder

        fun build(): AppComponent
    }

    override fun inject(application: MagicRunesApp)

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)
    fun inject(fortuneFragment: FortuneFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(infoFragment: InfoFragment)
    fun inject(currentFortuneFragment: CurrentFortuneFragment)
    fun inject(fortuneDescriptionFragment: FortuneDescriptionFragment)
    fun inject(infoDescriptionFragment: InfoDescriptionFragment)
    fun inject(addCommentDialogFragment: AddCommentDialogFragment)
    fun inject(signInDialog: SignInDialog)

    fun inject(infoFragmentAdapter: InfoFragmentAdapter)
    fun inject(historyFragmentAdapter: HistoryFragmentAdapter)
    fun inject(fortuneFragmentAdapter: FortuneFragmentAdapter)

    fun inject(historyListPresenter: HistoryListPresenter)
    fun inject(fortuneListPresenter: FortuneListPresenter)

    fun inject(runeOfTheDayWidget: RuneOfTheDayWidget)

    fun getGoogleService(): IGoogleService
}