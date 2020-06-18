package com.module.coreapps.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings

object LocationManagerUtils {
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

//    fun enableLocationSettings(context: Context) {
//        val settingsIntent =
//            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//
//
//
//        this.startActivityForResult(settingsIntent, REQUEST_APP_SETTINGS_LOCATION)
//    }
}