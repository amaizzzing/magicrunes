package com.magicrunes.magicrunes.data.services.background

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.di.DaggerAwareWorkerFactory
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val NOTIFICATION_CHANNEL_ID = 12333

class AlarmManagerBroadcastReceiver: BroadcastReceiver(), CoroutineScope {
    override val coroutineContext: CoroutineContext = MagicRunesApp.backgroundTaskDispatcher

    @Inject
    lateinit var daggerAwareWorkerFactory: DaggerAwareWorkerFactory

    @Inject
    lateinit var runeOfTheDayInteractor: IRuneOfTheDayInteractor

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)
        launch {
            val rune = runeOfTheDayInteractor.createRuneOfTheDay()
            if (rune is BaseState.Success<*>) {
                val resultRune = rune.resultData as RuneOfTheDayModel
                val lastRuneFromHistory = runeOfTheDayInteractor.getLastRuneFromHistory()
                if (lastRuneFromHistory is BaseState.Success<*>) {
                    val resultHistoryLastRune = lastRuneFromHistory.resultData as HistoryRuneDbEntity
                    if (resultHistoryLastRune.isNotificationShow == 0) {
                        runeOfTheDayInteractor.updateNotificationShow(
                            1,
                            resultHistoryLastRune.date
                        )
                        sendNotification(
                            context!!,
                            NOTIFICATION_CHANNEL_ID,
                            resultRune
                        )
                    }
                } else {
                    sendNotification(
                        context!!,
                        NOTIFICATION_CHANNEL_ID,
                        resultRune
                    )
                }
            }
        }
    }

    private fun sendNotification(ctx: Context, id: Int, rune: RuneOfTheDayModel) = launch(Dispatchers.Main) {
        val intent = Intent(ctx, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val imageService = ImageService(MagicRunesApp.instance)

        val bitmap = imageService.getRuneBitmap(rune.image, rune.isReverse)
        val titleNotification = ctx.getString(R.string.app_name)
        val subtitleNotification = "${ctx.getString(R.string.widget_text)} ${rune.name}"
        val pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0)
        val notification = NotificationCompat.Builder(ctx,
            NOTIFICATION_CHANNEL
        )
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.fortune_icon_32)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = NotificationCompat.PRIORITY_MAX

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = Color.RED
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {
        const val NOTIFICATION_ID = "MAGICRUNES_NOTIFICATION_ID"
        const val NOTIFICATION_NAME = "MAGICRUNES_NOTIFICATION_NAME"
        const val NOTIFICATION_CHANNEL = "MAGICRUNES_NOTIFICATION_CHANNEL"
    }
}