package br.com.android.teajudo.ui.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val navController = findNavController(R.id.nav_host_main)

        setSupportActionBar(toolbar)

        NavigationUI.setupActionBarWithNavController(this, navController)

        val bottomNavigationView = findViewById<NavigationView>(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_main).navigateUp()

    override fun onBackPressed() { }

    fun getLocation(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                100)
            return
        }
        locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MapsActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}