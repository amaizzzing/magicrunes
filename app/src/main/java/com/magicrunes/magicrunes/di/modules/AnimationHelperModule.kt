package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.utils.animationUtils.FabAnimatorHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnimationHelperModule {
    @Singleton
    @Provides
    fun fabAnimatorHelper(): FabAnimatorHelper = FabAnimatorHelper()
}