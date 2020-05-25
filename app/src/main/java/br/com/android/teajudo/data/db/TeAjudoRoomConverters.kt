package br.com.android.teajudo.data.db

import androidx.room.TypeConverter
import br.com.android.teajudo.data.db.entities.StoreEntity
import com.google.gson.Gson

internal class TeAjudoRoomConverters {

    var gson = Gson()

    @TypeConverter
    fun itemStoreEntityToString(someObjects: MutableList<StoreEntity>?): String? {
        if (someObjects == null) {
            return null
        }
        return gson.toJson(someObjects)
    }

}