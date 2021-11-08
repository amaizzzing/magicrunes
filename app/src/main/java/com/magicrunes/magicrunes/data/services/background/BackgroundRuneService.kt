package com.magicrunes.magicrunes.data.services.background

import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.di.IWorkerFactory
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import javax.inject.Inject
import javax.inject.Provider

class BackgroundRuneService (
     appContext: Context,
     workerParams: WorkerParameters,
     private val runeOfTheDayInteractor: IRuneOfTheDayInteractor
): CoroutineWorker(appContext, workerParams), IBackgroundRuneService {
    override suspend fun doWork(): Result {
        val rune = runeOfTheDayInteractor.createRuneOfTheDay()
        if (rune is BaseState.Success<*>) {
            val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
            sendNotification(id, (rune.resultData as RuneOfTheDayModel))
        }

        return Result.success()
    }

    class Factory @Inject constructor(
        private val context: Provider<MagicRunesApp>,
        private val runeOfTheDayInteractor: Provider<IRuneOfTheDayInteractor>
    ) : IWorkerFactory<BackgroundRuneService> {
        override fun create(params: WorkerParameters): BackgroundRuneService {
            return BackgroundRuneService(context.get(), params, runeOfTheDayInteractor.get())
        }
    }

    private fun sendNotification(id: Int, rune: RuneOfTheDayModel) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val imageService = ImageService(MagicRunesApp.instance)

        val bitmap = imageService.getRuneBitmap(rune.image, rune.isReverse)
        val titleNotification = applicationContext.getString(R.string.app_name)
        val subtitleNotification = "${applicationContext.getString(R.string.widget_text)} ${rune.name}"
        val pendingIntent = getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.fortune_icon_32)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"
    }
}