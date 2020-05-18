package br.com.android.teajudo.ui.maps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R


class MapsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

         this.showLoginFragment(savedInstanceState)
    }

    private fun showLoginFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = MapsFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, null)
                .commit()
        }
    }

    override fun onBackPressed() { }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MapsActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}