package us.nowbe.nowbe.services

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import us.nowbe.nowbe.R
import us.nowbe.nowbe.ui.activities.SplashActivity
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage?) {
        if (message == null) return

        // Check if the notifications are enabled
        val notificationsEnabled = SharedPreferencesUtils.getNotificationsEnabled(this)
        if (!notificationsEnabled) return

        // Show the notification
        val messageData = message.data["message"]
        if (messageData != null) {
            createNotification(messageData)
        }
    }

    /**
     * Creates and shows a notification given the data of it
     */
    fun createNotification(data: String) {
        val mainIntent = Intent(this, SplashActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(SplashActivity::class.java)
        stackBuilder.addNextIntent(mainIntent)
        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this)
                .setTicker(data)
                .setSmallIcon(R.drawable.nowbe_logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(data)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, notification)
    }
}