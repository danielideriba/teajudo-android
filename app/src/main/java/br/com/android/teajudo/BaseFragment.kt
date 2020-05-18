package br.com.android.teajudo

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.android.teajudo.ui.maps.MapsActivity
import dagger.android.support.AndroidSupportInjection

open class BaseFragment: Fragment(){
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
}