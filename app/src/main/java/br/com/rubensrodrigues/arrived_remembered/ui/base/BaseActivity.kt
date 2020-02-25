package br.com.rubensrodrigues.arrived_remembered.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import br.com.rubensrodrigues.arrived_remembered.util.Constants
import br.com.rubensrodrigues.arrived_remembered.util.extensions.cancelNotification

abstract class BaseActivity(@LayoutRes private val layoutRes: Int): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        cancelNotification(intent.getIntExtra(Constants.NOTIFICATION_ID, -2))
    }
}