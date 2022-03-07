package com.magicrunes.magicrunes.di.modules

import com.magicrunes.magicrunes.data.services.background.AlarmManagerBroadcastReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverModule {
    @ContributesAndroidInjector
    abstract fun alarmManagerBroadcastReceiver() : AlarmManagerBroadcastReceiver
}