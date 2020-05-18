package br.com.android.teajudo.ui

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.android.teajudo.R
import br.com.android.teajudo.ui.maps.MapsActivity
import br.com.android.teajudo.ui.permissionRequests.PermissionRequestsActivity
import br.com.android.teajudo.utils.Constants.SHARED_KEY
import br.com.android.teajudo.utils.SharedPreferencesUtils
import com.module.verifyconnectivitymodule.receivers.ConnectivityReceiver
import com.module.verifyconnectivitymodule.ui.WarningScreenActivity
import timber.log.Timber


class LauncherActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var currentBroadcast: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        configureReceiver()
    }

    private fun configureReceiver() {
        currentBroadcast = ConnectivityReceiver()
        this.registerReceiver(currentBroadcast, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }

    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            Timber.d("You are offline now.")
            WarningScreenActivity.start(this)
        } else {
            Timber.d("You are online now.")
            if(SharedPreferencesUtils.getBooleanPreference(this, SHARED_KEY, false)){
                MapsActivity.start(this)
            } else {
                PermissionRequestsActivity.start(this)
            }
        }
    }
}
