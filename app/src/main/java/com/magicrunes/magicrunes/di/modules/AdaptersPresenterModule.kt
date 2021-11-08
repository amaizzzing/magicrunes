package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.ui.adapters.presenters.fortune.FortuneListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.fortune.IFortuneListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.history.HistoryListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.history.IHistoryListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.info.IInfoListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.info.InfoListPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.infodescription.IInfoDescriptionPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.infodescription.InfoDescriptionPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.viewpager.IViewPagerPresenter
import com.magicrunes.magicrunes.ui.adapters.presenters.viewpager.ViewPagerPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdaptersPresenterModule {
    @Singleton
    @Provides
    fun infoListPresenter(): IInfoListPresenter = InfoListPresenter()

    @Singleton
    @Provides
    fun historyListPresenter(): IHistoryListPresenter = HistoryListPresenter()

    @Singleton
    @Provides
    fun fortuneListPresenter(): IFortuneListPresenter = FortuneListPresenter()

    @Singleton
    @Provides
    fun viewPagerPresenter(resourceService: IResourceService): IViewPagerPresenter = ViewPagerPresenter(resourceService)

    @Singleton
    @Provides
    fun infoDescriptionPresenter(): IInfoDescriptionPresenter = InfoDescriptionPresenter()
}