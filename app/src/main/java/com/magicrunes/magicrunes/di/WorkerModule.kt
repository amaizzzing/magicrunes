package com.magicrunes.magicrunes.di

import androidx.work.ListenableWorker
import com.magicrunes.magicrunes.data.services.background.BackgroundRuneService
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(BackgroundRuneService::class)
    fun bindRuneService(factory: BackgroundRuneService.Factory): IWorkerFactory<out ListenableWorker>
    // every time you add a worker, add a binding here
}