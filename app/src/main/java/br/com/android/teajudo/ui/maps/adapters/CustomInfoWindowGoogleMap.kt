package br.com.android.teajudo.ui.maps.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import br.com.android.teajudo.R
import br.com.android.teajudo.data.remote.model.Store
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map_marker_info.view.*

class CustomInfoWindowGoogleMap(context: Context): GoogleMap.InfoWindowAdapter {
    private var context = context

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker?): View? {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.fragment_map_marker_info, null)
        var mInfoWindow: Store? = marker?.tag as Store?

        mInfoView.txtMarkerName.text = mInfoWindow?.type

        return mInfoView
    }
}