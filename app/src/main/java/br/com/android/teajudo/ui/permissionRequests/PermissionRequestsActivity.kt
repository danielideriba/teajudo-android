package br.com.android.teajudo.ui.permissionRequests

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R
import br.com.android.teajudo.ui.maps.MapsActivity
import br.com.android.teajudo.utils.Constants.REQUEST_APP_SETTINGS
import br.com.android.teajudo.utils.Constants.SHARED_KEY
import br.com.android.teajudo.utils.SharedPreferencesUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.module.coreapps.helpers.LocationHelperGoogleServices
import kotlinx.android.synthetic.main.activity_permission.*
import timber.log.Timber


class PermissionRequestsActivity: BaseActivity(), MultiplePermissionsListener {
    private val requiredPermissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PermissionRequestsActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        settingActions()
    }

    private fun settingActions(){
        allowPermissions.setOnClickListener {
            if(!LocationHelperGoogleServices().isLocationEnabled(this)){
                PermissionRequestsLocationActivity.start(this)
            } else {
                setupPermissions()
            }
        }
    }

    private fun setupPermissions(){
        Dexter.withContext(this)
            .withPermissions(requiredPermissions)
            .withListener(this)
            .withErrorListener {
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivity(intent)
            }
            .check()
    }

    override fun onBackPressed() { }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        report?.let {
            if(report.areAllPermissionsGranted()){
                SharedPreferencesUtils.setBooleanPreference(this, SHARED_KEY, true)
                if(LocationHelperGoogleServices().isLocationEnabled(this)){
                    MapsActivity.start(this)
                    finish()
                } else {
                    PermissionRequestsLocationActivity.start(this)
                    finish()
                }
            }

            if (report.grantedPermissionResponses.size == 0){
                this.goToSettings()
            }
        }
    }

    fun hasPermissions(context: Context, permissions: List<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun goToSettings(){
        val myAppSettings = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_APP_SETTINGS) {
            if (hasPermissions(this, requiredPermissions)) {
                SharedPreferencesUtils.setBooleanPreference(this, SHARED_KEY, true)

                PermissionRequestsLocationActivity.start(this)
                finish()
            } else {
                this.goToSettings()
            }
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: List<PermissionRequest?>?,
        token: PermissionToken?
    ) {
        token?.cancelPermissionRequest()
    }
}