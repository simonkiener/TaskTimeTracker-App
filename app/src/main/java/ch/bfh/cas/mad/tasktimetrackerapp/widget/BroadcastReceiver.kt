package ch.bfh.cas.mad.tasktimetrackerapp.widget

import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager

open class BroadcastReceiver: BroadcastReceiver() {
     override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                WidgetProvider.ACTION_WIDGET_BUTTON_1 -> {
                    val localIntent = Intent("ACTION_WIDGET_BUTTON_1_RECEIVED")
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_2 -> {
                    val localIntent = Intent("ACTION_WIDGET_BUTTON_2_RECEIVED")
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_3 -> {
                    val localIntent = Intent("ACTION_WIDGET_BUTTON_3_RECEIVED")
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_4 -> {
                    val localIntent = Intent("ACTION_WIDGET_BUTTON_4_RECEIVED")
                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
                }
            }
        }
    }