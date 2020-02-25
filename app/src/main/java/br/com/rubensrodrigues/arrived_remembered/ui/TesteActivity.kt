package br.com.rubensrodrigues.arrived_remembered.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.util.Constants
import br.com.rubensrodrigues.arrived_remembered.util.PermissionHelper
import br.com.rubensrodrigues.arrived_remembered.util.extensions.cancelNotification
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_teste.*
import org.jetbrains.anko.toast

class TesteActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_LOCATION = 1002
    private val REQUEST_AUTOCOMPLETE_PLACE = 1001

    private lateinit var locationTyped: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        cancelNotification(intent.getIntExtra(Constants.NOTIFICATION_ID, -2))
        setListeners()
    }

    private fun setListeners() {
        edittext.setOnClickListener {
            openAutocompletePlaceDialog()
        }

        button.setOnClickListener {
            PermissionHelper.checkForPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                REQUEST_PERMISSION_LOCATION
            ){
                showCoordination()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!Places.isInitialized()) Places.deinitialize()
    }

    private fun openAutocompletePlaceDialog() {
        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.api_key))

        val intentAutoComplete = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        ).build(this)

        startActivityForResult(intentAutoComplete, REQUEST_AUTOCOMPLETE_PLACE)
    }

    private var counter: Int = 0

    @SuppressLint("MissingPermission")
    private fun showCoordination() {
        val systemServiceLocation = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        systemServiceLocation.requestSingleUpdate(LocationManager.GPS_PROVIDER, object: LocationListener{
            override fun onLocationChanged(p0: Location?) {
                if(::locationTyped.isInitialized){
                    textview.text = locationTyped.distanceTo(p0).toString()
                } else {
                    counter += 1
                    toast("Escolha primeiro o endereço de destino. Contador - $counter")
                    Log.i("REQUEST_LOCATION", "$counter")
                }

            }
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
            override fun onProviderEnabled(p0: String?) {}
            override fun onProviderDisabled(p0: String?) {
                toast("Location disabled")
            }
        }, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_AUTOCOMPLETE_PLACE && resultCode == Activity.RESULT_OK){
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(it)

                locationTyped = Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = place.latLng?.latitude!!
                    longitude = place.latLng?.longitude!!
                }

                edittext.setText(place.name)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_PERMISSION_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showCoordination()
                } else {
                    toast("Permissão à localização negada")
                    finish()
                }
            }
        }
    }
}
