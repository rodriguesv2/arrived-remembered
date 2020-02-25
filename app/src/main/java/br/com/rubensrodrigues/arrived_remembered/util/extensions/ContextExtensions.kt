package br.com.rubensrodrigues.arrived_remembered.util.extensions

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.util.Constants

inline fun <reified T: Activity>Context.showNotification(title: String, text: String, id: Int = -1){
    val notificationIntent = Intent(this, T::class.java).apply { putExtra(Constants.NOTIFICATION_ID, id) }
    val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
    } else {
        NotificationCompat.Builder(this)
    }

    val notification = notificationBuilder
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentIntent(pendingIntent)
        .build()

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(id, notification)
}

fun Context.cancelNotification(id: Int){
    if (id > -2){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id)
    }
}