package com.module.coreapps.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class CoreRoomConverters {

    var gson = Gson()

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun stringMutableListToString(someObjects: MutableList<String>?): String? {
        if (someObjects == null) {
            return null
        }
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToStringMutableList(data: String?): MutableList<String> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<MutableList<String>>() {}.type

        return gson.fromJson(data, listType)
    }

}