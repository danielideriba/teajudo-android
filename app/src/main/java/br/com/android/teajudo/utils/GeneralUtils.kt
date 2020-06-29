package br.com.android.teajudo.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import br.com.android.teajudo.R
import br.com.android.teajudo.data.remote.VolunteerType

object GeneralUtils {
    fun getTypeCategory(context: Context, type: String): String{
        return when (type) {
            VolunteerType.VOLUNTEER.type -> {
                return context.resources.getString(R.string.volunteers_title)
            }
            VolunteerType.STORE.type -> {
                return context.resources.getString(R.string.local_business_title)
            }
            VolunteerType.USERTYPE.type -> {
                return context.resources.getString(R.string.user_title)
            }
            else -> context.resources.getString(R.string.type_not_found)
        }
    }

    fun showAndHideButton(option: Boolean?): Int {
        var viewStatus = View.VISIBLE
        if(option != null) {
            if(option) {
                viewStatus = View.VISIBLE
            } else {
                viewStatus = View.GONE
            }
        }

        return viewStatus
    }
}