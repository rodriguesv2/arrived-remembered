package br.com.rubensrodrigues.arrived_remembered.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.util.PermissionHelper
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_teste.*
import org.jetbrains.anko.toast

class TesteActivity : AppCompatActivity() {

    private val REQUEST_LOCATION = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

//        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.api_key))
//
//        val intentAutoComplete = Autocomplete.IntentBuilder(
//            AutocompleteActivityMode.OVERLAY,
//            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//        ).build(this)
//
//        startActivityForResult(intentAutoComplete, 1001)

        PermissionHelper.checkForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
            REQUEST_LOCATION
        ){
            showCoordination()
        }

    }

    @SuppressLint("MissingPermission")
    private fun showCoordination() {
        val systemService = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = systemService.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        textview.text = "${location.latitude.toFloat()}, ${location.longitude.toFloat()}"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == Activity.RESULT_OK){
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(it)
                var str = ""
                str += "ID: ${place.id}\n\n"
                str += "NAME: ${place.name}\n\n"
                str += "ADDRESS: ${place.address}\n\n"
                str += "CORD: ${place.latLng}"

                textview.text = str
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
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showCoordination()
                } else {
                    toast("Permissão a localização negada")
                    finish()
                }
            }
        }
    }
}
