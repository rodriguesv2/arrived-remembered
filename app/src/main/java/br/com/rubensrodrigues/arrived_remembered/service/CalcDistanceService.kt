package br.com.rubensrodrigues.arrived_remembered.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.ui.TesteActivity
import org.jetbrains.anko.toast

class CalcDistanceService : Service() {


    private val timer by lazy {
        object: CountDownTimer(2000, 20){
            override fun onFinish() {
                toast("time end")
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

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, TesteActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "locationServiceChannel")
            .setContentTitle("Serviço de localização ativo")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

}
