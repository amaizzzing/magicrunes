package com.magicrunes.magicrunes.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Implementation of App Widget functionality.
 */
class RuneOfTheDayWidget : AppWidgetProvider(), CoroutineScope {
    @Inject
    lateinit var runeOfTheDayInteractor: IRuneOfTheDayInteractor

    @Inject
    lateinit var imageService: ImageService

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        this.launch(coroutineContext) {
            val runeInfo = getRuneInfo()
            withContext(Dispatchers.Main) {
                for (appWidgetId in appWidgetIds) {
                    runeInfo?.let {
                        updateAppWidget(context, appWidgetManager, appWidgetId, runeInfo.first, runeInfo.second)
                    }
                }
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    private suspend fun getRuneInfo(): Pair<String, Bitmap>? {
        val rune = runeOfTheDayInteractor.createRuneOfTheDay()
        if (rune is BaseState.Success<*>) {
            (rune.resultData as RuneOfTheDayModel).apply {
                return name to imageService.getRuneBitmap(image, isReverse)
            }
        }

        return null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        MagicRunesApp.appComponent.inject(this)
        super.onReceive(context, intent)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    runeName: String,
    bitmap: Bitmap
) {
    RemoteViews(context.packageName, R.layout.rune_of_the_day_widget).apply {
        setTextViewText(R.id.appwidget_text, context.getString(R.string.widget_text))
        setTextViewText(R.id.rune_name_widget, runeName)
        setImageViewBitmap(R.id.image_widget, bitmap)

        setOnClickPendingIntent(R.id.root_widget,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0))

        appWidgetManager.updateAppWidget(appWidgetId, this)
    }
}