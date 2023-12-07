package com.sazzdev.qrcodekiller

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService: FirebaseMessagingService() {
    private val CHANNEL_ID = "001"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Firebase", "New token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("Firebase", "From: ${remoteMessage.from}")
        Log.d("Firebase", "Message data payload: ${remoteMessage.data}")
        Log.d("Firebase", "Message Notification Body: ${remoteMessage.notification?.body}")

        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}