package com.magicrunes.magicrunes

import androidx.work.*
import com.magicrunes.magicrunes.data.services.background.BackgroundRuneService
import com.magicrunes.magicrunes.di.DaggerAwareWorkerFactory
import com.magicrunes.magicrunes.di.components.AppComponent
import com.magicrunes.magicrunes.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

val WORK_NAME = "RUNE_WORK"

class MagicRunesApp: DaggerApplication() {
    @Inject
    lateinit var daggerAwareWorkerFactory: DaggerAwareWorkerFactory

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(daggerAwareWorkerFactory)
            .build()
        WorkManager.initialize(this, config)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = applicationInjector() as AppComponent
        initDispatchers()

        configureWorkManager()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<BackgroundRuneService>(24, TimeUnit.HOURS)
                .setInitialDelay(1, TimeUnit.HOURS)
                .setConstraints(Constraints.NONE)
                .build())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    fun initDispatchers() {
        backgroundTaskDispatcher = Dispatchers.Default

        orderedBackgroundTaskDispatcher = Executors.newSingleThreadExecutor {
            Thread(it, "OrderedDispatcher")
        }.asCoroutineDispatcher()
    }
    companion object {
        lateinit var instance: MagicRunesApp

        lateinit var appComponent: AppComponent

        lateinit var backgroundTaskDispatcher: CoroutineDispatcher

        lateinit var orderedBackgroundTaskDispatcher: ExecutorCoroutineDispatcher
    }
}