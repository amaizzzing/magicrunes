package com.magicrunes.magicrunes.data.services.background

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.di.DaggerAwareWorkerFactory
import com.magicrunes.magicrunes.domain.interactors.notificationinteractor.INotificationInteractor
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

const val RUNE_NOTIFICATION_CHANNEL_ID = 12333
const val FORTUNE_NOTIFICATION_CHANNEL_ID = 11333

class AlarmManagerBroadcastReceiver: BroadcastReceiver(), CoroutineScope {
    override val coroutineContext: CoroutineContext = MagicRunesApp.backgroundTaskDispatcher

    @Inject
    lateinit var daggerAwareWorkerFactory: DaggerAwareWorkerFactory

    @Inject
    lateinit var runeOfTheDayInteractor: IRuneOfTheDayInteractor

    @Inject
    lateinit var notificationInteractor: INotificationInteractor

    @Inject
    lateinit var imageService: ImageService

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
                        notificationInteractor.updateNotificationShow(
                            1,
                            resultHistoryLastRune.date
                        )
                        context?.let { ctx ->
                            sendNotification(
                                ctx,
                                RUNE_NOTIFICATION_CHANNEL_ID,
                                NotificationType.RUNE_NOTIFICATION,
                                imageService.getRuneBitmap(resultRune.image, resultRune.isReverse),
                                "${ctx.getString(R.string.widget_text)} ${resultRune.name}"
                            )
                        }
                    } else {
                        val randomFortune = notificationInteractor.getRandomFortune().resultData
                        context?.let { ctx ->
                            sendNotification(
                                ctx,
                                FORTUNE_NOTIFICATION_CHANNEL_ID,
                                NotificationType.FORTUNE_NOTIFICATION,
                                imageService.getRuneBitmap(randomFortune.localImageName, false),
                                "${ctx.getString(R.string.fortune_notification_text)} ${randomFortune.nameFortune}"
                            )
                        }
                    }
                } else {
                    context?.let { ctx ->
                        sendNotification(
                            ctx,
                            RUNE_NOTIFICATION_CHANNEL_ID,
                            NotificationType.RUNE_NOTIFICATION,
                            imageService.getRuneBitmap(resultRune.image, resultRune.isReverse),
                            "${ctx.getString(R.string.widget_text)} ${resultRune.name}"
                        )
                    }
                }
            }
        }
    }

    private fun sendNotification(
        ctx: Context,
        id: Int,
        notificationType: NotificationType,
        bitmap: Bitmap,
        subtitle: String
    ) = launch(Dispatchers.Main) {
        val intent = Intent(ctx, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val titleNotification = ctx.getString(R.string.app_name)
        val pendingIntent = NavDeepLinkBuilder(ctx)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(
                if (notificationType == NotificationType.RUNE_NOTIFICATION) {
                    R.id.navigation_main
                } else {
                    R.id.navigation_fortune
                }
            )
            .createPendingIntent()
        val notification = NotificationCompat.Builder(
            ctx,
            NOTIFICATION_CHANNEL
        )
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.fortune_icon_32)
            .setContentTitle(titleNotification).setContentText(subtitle)
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

        enum class NotificationType(id: Int) {
            RUNE_NOTIFICATION(RUNE_NOTIFICATION_CHANNEL_ID),
            FORTUNE_NOTIFICATION(FORTUNE_NOTIFICATION_CHANNEL_ID)
        }
    }
}