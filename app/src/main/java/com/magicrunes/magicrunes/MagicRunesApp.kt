package com.magicrunes.magicrunes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.magicrunes.magicrunes.data.services.background.AlarmManagerBroadcastReceiver
import com.magicrunes.magicrunes.di.components.AppComponent
import com.magicrunes.magicrunes.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.joda.time.DateTime
import java.util.concurrent.Executors

class MagicRunesApp: DaggerApplication() {
    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = applicationInjector() as AppComponent

        initDispatchers()

        createAlarmManager()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    private fun createAlarmManager() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmManagerBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1001,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            DateTime.now().millis,
            AlarmManager.INTERVAL_HALF_DAY,
            pendingIntent
        )
    }

    private fun initDispatchers() {
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