package ch.bfh.cas.mad.tasktimetrackerapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import ch.bfh.cas.mad.tasktimetrackerapp.R

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach { appWidgetId ->
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Intent für jeden Button
            val button1Intent = Intent(context, WidgetProvider::class.java).apply {
                action = ACTION_WIDGET_BUTTON_1
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val button2Intent = Intent(context, WidgetProvider::class.java).apply {
                action = ACTION_WIDGET_BUTTON_2
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val button3Intent = Intent(context, WidgetProvider::class.java).apply {
                action = ACTION_WIDGET_BUTTON_3
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val button4Intent = Intent(context, WidgetProvider::class.java).apply {
                action = ACTION_WIDGET_BUTTON_4
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            // PendingIntent für jeden Button
            val pendingIntent1 = PendingIntent.getBroadcast(context, 0, button1Intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val pendingIntent2 = PendingIntent.getBroadcast(context, 0, button2Intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val pendingIntent3 = PendingIntent.getBroadcast(context, 0, button3Intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val pendingIntent4 = PendingIntent.getBroadcast(context, 0, button4Intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            views.setOnClickPendingIntent(R.id.widget_button1, pendingIntent1)
            views.setOnClickPendingIntent(R.id.widget_button2, pendingIntent2)
            views.setOnClickPendingIntent(R.id.widget_button3, pendingIntent3)
            views.setOnClickPendingIntent(R.id.widget_button4, pendingIntent4)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null && context != null) {
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            when (intent.action) {
                ACTION_WIDGET_BUTTON_1 -> updateWidgetViews(views, 1)
                ACTION_WIDGET_BUTTON_2 -> updateWidgetViews(views, 2)
                ACTION_WIDGET_BUTTON_3 -> updateWidgetViews(views, 3)
                ACTION_WIDGET_BUTTON_4 -> updateWidgetViews(views, 4)
            }

            AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
        }
    }

    private fun updateWidgetViews(views: RemoteViews, buttonNumber: Int) {
        val buttons = listOf(R.id.widget_button1, R.id.widget_button2, R.id.widget_button3, R.id.widget_button4)
        buttons.forEachIndexed { index, buttonId ->
            views.setInt(buttonId, "setBackgroundResource", if (index + 1 == buttonNumber) R.drawable.round_button_activ else R.drawable.round_button_inactiv)
        }
    }

    companion object {
        const val ACTION_WIDGET_BUTTON_1 = "ACTION_WIDGET_BUTTON_1"
        const val ACTION_WIDGET_BUTTON_2 = "ACTION_WIDGET_BUTTON_2"
        const val ACTION_WIDGET_BUTTON_3 = "ACTION_WIDGET_BUTTON_3"
        const val ACTION_WIDGET_BUTTON_4 = "ACTION_WIDGET_BUTTON_4"
    }
}