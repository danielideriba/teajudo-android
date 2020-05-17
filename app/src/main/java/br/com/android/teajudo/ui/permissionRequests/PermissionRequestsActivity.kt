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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class PermissionRequestsActivity: BaseActivity(), MultiplePermissionsListener {
    private val REQUEST_APP_SETTINGS = 168

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

        this.showLoginFragment(savedInstanceState)
        this.setupPermissions()
    }

    private fun showLoginFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = PermissionRequestFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, null)
                .commit()
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
                val intent = MapsActivity.newIntent(this)
                startActivity(intent)
                finish()
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
                val intent = MapsActivity.newIntent(this)
                startActivity(intent)
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