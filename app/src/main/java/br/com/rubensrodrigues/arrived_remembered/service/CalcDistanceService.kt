package br.com.rubensrodrigues.arrived_remembered.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.ui.TesteActivity
import br.com.rubensrodrigues.arrived_remembered.util.Constants
import br.com.rubensrodrigues.arrived_remembered.util.extensions.showNotification
import br.com.rubensrodrigues.arrived_remembered.util.helper.LocationHelper
import kotlin.random.Random

class CalcDistanceService : Service() {

    private val itaquera = Location(LocationManager.GPS_PROVIDER).apply {
        latitude = -23.5319848
        longitude = -46.4407301
    }

    private val timer by lazy {
        object: CountDownTimer(60*1000*10, 1000){
            override fun onFinish() {
                LocationHelper.currentCoordination(
                    context = this@CalcDistanceService,
                    onLocationChanged = {
                        it?.let {
                            val distance = it.distanceTo(itaquera)
                            if (distance < 50){
                                val randomId = Random.nextInt(0, 1000)
                                showNotification<TesteActivity>("Lembrete", "Chegamos em Itaquera", 10)
                            }
                        }
                    },
                    onProviderDisabled = {

                    }
                )
                timerStart()
            }

            override fun onTick(p0: Long) {}
        }
    }

    override fun onCreate() {
        super.onCreate()

        timerStart()
    }

    private fun timerStart(){
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, TesteActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
            .setContentTitle("Serviço de localização ativo")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

}
