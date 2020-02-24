package br.com.rubensrodrigues.arrived_remembered.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rubensrodrigues.arrived_remembered.R
import br.com.rubensrodrigues.arrived_remembered.service.CalcDistanceService

class Teste2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste2)

        startService(Intent(this, CalcDistanceService::class.java))
    }
}
