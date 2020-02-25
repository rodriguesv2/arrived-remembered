package br.com.rubensrodrigues.arrived_remembered

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import br.com.rubensrodrigues.arrived_remembered.util.Constants.NOTIFICATION_CHANNEL

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        createNofificationChannel()
    }

    private fun createNofificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                "Localização Ativa",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }
}