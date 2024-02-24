package ch.bfh.cas.mad.tasktimetrackerapp.widget

import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver

class BroadcastReceiver: BroadcastReceiver() {
     override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                WidgetProvider.ACTION_WIDGET_BUTTON_1 -> {
                    println("Widget_Button 1 pressed")
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_2 -> {
                    println("Widget_Button 2 pressed")
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_3 -> {
                    println("Widget_Button 3 pressed")
                }
                WidgetProvider.ACTION_WIDGET_BUTTON_4 -> {
                    println("Widget_Button 4 pressed")
                }
            }
        }
    }