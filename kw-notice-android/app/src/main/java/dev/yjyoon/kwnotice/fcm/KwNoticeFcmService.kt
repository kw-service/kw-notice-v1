package dev.yjyoon.kwnotice.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.view.main.MainActivity
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity


class KwNoticeFcmService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if(remoteMessage.notification != null) {
            Log.d("fcm", remoteMessage.notification!!.title + remoteMessage.notification!!.body)
            sendNotification(remoteMessage.notification!!)
        }


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {

        }
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {

        val intent = Intent(this, WebViewActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.putExtra("url", "https://www.google.com")
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val channelId = "KW Notice notification channel id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder =  NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "KW Notice notification channel name"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}