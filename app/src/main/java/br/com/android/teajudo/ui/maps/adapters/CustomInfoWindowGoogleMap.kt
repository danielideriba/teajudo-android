package br.com.android.teajudo.ui.maps.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.utils.GeneralUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map_marker_info.view.*
import timber.log.Timber

class CustomInfoWindowGoogleMap(context: Context): GoogleMap.InfoWindowAdapter {
    private var context = context

    override fun getInfoContents(marker: Marker?): View? {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.fragment_map_marker_info, null)
        var mInfoWindow: StoreEntity? = marker?.tag as StoreEntity?

        mInfoWindow.let {
            if(it != null) {
                mInfoView.txtMarkerType.text = GeneralUtils.getTypeCategory(context, it.type)
                mInfoView.txtMarkerName.text = it.name

                tvVisibility(mInfoView.emailButton, it.email)
                tvVisibility(mInfoView.phoneButton, it.phone)
                tvVisibility(mInfoView.whatsappButton, it.whatapp)
            }
        }
        return mInfoView
    }

    private fun tvVisibility(button: TextView, element: String) {
        if (element != "") {
            button.visibility = View.VISIBLE
        } else {
            button.visibility = View.GONE
        }
    }

    private fun tvVisibility(button: TextView, element: Int) {
        if (element == 1) {
            button.visibility = View.VISIBLE
        } else {
            button.visibility = View.GONE
        }
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}