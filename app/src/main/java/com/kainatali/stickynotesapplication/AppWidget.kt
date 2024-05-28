package com.kainatali.stickynotesapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class AppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds){
                val intent = Intent(context, MainActivity::class.java)

                // Intent to execute later by a different app or the system
                // Used in notifications, home widgets, and such
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                // Used to inflate the view that we want to show outside of app.
                // Allows us to alter the UI and view of our app outside the context of our app.
                // Primarily used in scenarios where you want to update the UI of widgets,
                // notifications, or custom views displayed in another process.
                val remoteView = RemoteViews(context?.packageName!!, R.layout.homescreen_widget_layout)
                remoteView.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent)
                appWidgetManager?.updateAppWidget(appWidgetId, remoteView)
            }
        }
    }
}