package br.com.android.teajudo.ui.permissionRequests

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R
import br.com.android.teajudo.ui.maps.MapsActivity
import br.com.android.teajudo.utils.Constants.REQUEST_APP_SETTINGS_LOCATION
import br.com.android.teajudo.utils.Constants.SHARED_KEY_LOCATION
import br.com.android.teajudo.utils.SharedPreferencesUtils
import br.com.grupofleury.core.utils.DialogUtils
import com.module.coreapps.utils.LocationManagerUtils
import kotlinx.android.synthetic.main.activity_permission_location.*

class PermissionRequestsLocationActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_location)

        if(LocationManagerUtils.isLocationEnabled(this)) {
            MapsActivity.start(this)
            finish()
        }

        setupButtons()
    }

    private fun setupButtons(){
        allowLocations.setOnClickListener {
            enableLocationSettings()
        }
    }

    //Move to LocationManagerUtils
    private fun enableLocationSettings() {
        val settingsIntent =
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        this.startActivityForResult(settingsIntent, REQUEST_APP_SETTINGS_LOCATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_APP_SETTINGS_LOCATION) {
            if (LocationManagerUtils.isLocationEnabled(this)) {
                SharedPreferencesUtils.setBooleanPreference(this, SHARED_KEY_LOCATION, true)
                MapsActivity.start(this)
                finish()
            } else {
                this.enableLocationSettings()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PermissionRequestsLocationActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}