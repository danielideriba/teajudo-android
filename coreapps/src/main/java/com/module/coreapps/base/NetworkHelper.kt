package com.module.coreapps.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper(val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < 23) {
            @Suppress("DEPRECATION")
            val ni = cm.activeNetworkInfo
            if (ni != null) {
                @Suppress("DEPRECATION")
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n: Network? = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                nc?.let {
                    return it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                }
            }
        }

        return false
    }
}