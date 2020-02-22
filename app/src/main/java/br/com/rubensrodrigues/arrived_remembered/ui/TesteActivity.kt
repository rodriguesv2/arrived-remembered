package br.com.rubensrodrigues.arrived_remembered.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rubensrodrigues.arrived_remembered.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class TesteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)


        if (!Places.isInitialized())
            Places.initialize(this, getString(R.string.api_key))

        val intentAutoComplete = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        ).build(this)

        startActivityForResult(intentAutoComplete, 1001)
    }
}
