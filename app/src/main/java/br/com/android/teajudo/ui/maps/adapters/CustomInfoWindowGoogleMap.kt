package br.com.android.teajudo.ui.maps.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.remote.model.Store
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map_marker_info.view.*
import timber.log.Timber

class CustomInfoWindowGoogleMap(context: Context): GoogleMap.InfoWindowAdapter {
    private var mInfoView: View? =
        (context as Activity).layoutInflater.inflate(R.layout.fragment_map_marker_info, null)

    override fun getInfoContents(marker: Marker?): View? {
        var mInfoWindow: StoreEntity? = marker?.tag as StoreEntity?

        mInfoWindow?.idStore

        mInfoView.let {
            if (it != null) {
                it.txtMarkerName.text = mInfoWindow?.name
                it.txtMarkerType.text = mInfoWindow?.type
            }
        }
        return mInfoView
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return mInfoView
    }
}