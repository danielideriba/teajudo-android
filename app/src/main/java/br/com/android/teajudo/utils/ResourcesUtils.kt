package br.com.android.teajudo.utils

import br.com.android.teajudo.R
import br.com.android.teajudo.data.remote.VolunteerType
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object ResourcesUtils {
    fun mapIconBitmap(type: String): BitmapDescriptor? {
        var drawable: Int = R.drawable.teajudome_logo
        when(type){
            VolunteerType.STORE.type -> {
                drawable = R.drawable.feira
            }
            VolunteerType.VOLUNTEER.type -> {
                drawable = R.drawable.volunteer
            }
            VolunteerType.USERTYPE.type -> {
                drawable = R.drawable.user
            }
        }

        return BitmapDescriptorFactory.fromResource(drawable)
    }
}