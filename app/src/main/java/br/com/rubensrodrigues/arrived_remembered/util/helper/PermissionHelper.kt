package br.com.rubensrodrigues.arrived_remembered.util.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionHelper {

    private fun shouldAskPermission(context: Context, permission: String): Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return true
        }

        return false
    }

    fun checkForPermission(context: Context, permission: String, requestCode: Int, permissionAlreadyGranted: (() -> Unit)? = null){
        if (shouldAskPermission(
                context,
                permission
            )
        ){
            ActivityCompat.requestPermissions(context as Activity, listOf(permission).toTypedArray(), requestCode)
        } else {
            permissionAlreadyGranted?.invoke()
        }
    }
}