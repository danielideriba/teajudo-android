package com.module.coreapps.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import br.com.grupofleury.core.utils.DialogUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import timber.log.Timber


class LocationHelperGoogleServices() : GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener {
    private val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationManager: LocationManager
    lateinit var pCallback: LocationHelperCallback

    private val sUINTERVAL = (2 * 1000).toLong()
    private val sFINTERVAL: Long = 2000

    private lateinit var activity: Activity

    fun initLocation(activity: Activity, context: Context) {
        this.activity = activity

        if(!isLocationEnabled(context)){
            var dialog = DialogUtils.simpleDialog(context, "Aviso", "Você será redirecionado para as configurações")

            dialog.setPositiveButton("OK") { dialog, which ->
                enableLocationSettings(activity)
            }
        }

        mGoogleApiClient = GoogleApiClient.Builder(activity).apply {
            addConnectionCallbacks(this@LocationHelperGoogleServices)
            addConnectionCallbacks(this@LocationHelperGoogleServices)
            addApi(LocationServices.API)
        }.build()
        mLocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun isGooglePlayServicesAvailable(activity: Activity, context: Context): Boolean {
        val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
        val apiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode: Int = apiAvailability.isGooglePlayServicesAvailable(context)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                    .show()
            } else {
                Timber.d("This device is not supported.")
                activity.finish()
            }
            return false
        }
        Timber.d("This device is supported.")
        return true
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun enableLocationSettings(activity: Activity) {
        val settingsIntent =
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivity(settingsIntent)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(activity: Activity) {
        mLocationRequest = LocationRequest.create().apply{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = sUINTERVAL
            fastestInterval = sFINTERVAL
        }
        LocationServices.getFusedLocationProviderClient(activity)
    }

    override fun onConnected(bundle: Bundle?) {
        mGoogleApiClient.let {
            if (it != null && it.isConnected) {
                it.connect()
            }
        }
    }

    override fun onConnectionSuspended(int: Int) { }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                    this.activity,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST
                )
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        } else {
            Timber.d("Location services connection failed with code "
                    + connectionResult.errorCode)
        }
    }

    override fun onLocationChanged(location: Location) {
        pCallback.updateUi(location)
    }
}