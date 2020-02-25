package br.com.rubensrodrigues.arrived_remembered.util.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_teste.*
import org.jetbrains.anko.toast

object LocationHelper {

    @SuppressLint("MissingPermission")
    fun currentCoordination(
        context: Context,
        onLocationChanged: ((location: Location?)->Unit),
        onProviderEnabled: ((message: String?)->Unit)? = null,
        onProviderDisabled: ((message: String?)->Unit)? = null){
            val systemServiceLocation = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            systemServiceLocation.requestSingleUpdate(LocationManager.GPS_PROVIDER, object:
                LocationListener {
                override fun onLocationChanged(p0: Location?) { onLocationChanged.invoke(p0) }
                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
                override fun onProviderEnabled(p0: String?) { onProviderEnabled?.invoke(p0) }
                override fun onProviderDisabled(p0: String?) { onProviderDisabled?.invoke(p0) }
            }, null)
    }
}