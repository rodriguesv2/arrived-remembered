package br.com.rubensrodrigues.arrived_remembered.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rubensrodrigues.arrived_remembered.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_teste.*

class TesteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.api_key))

        val intentAutoComplete = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        ).build(this)

        startActivityForResult(intentAutoComplete, 1001)


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
}
