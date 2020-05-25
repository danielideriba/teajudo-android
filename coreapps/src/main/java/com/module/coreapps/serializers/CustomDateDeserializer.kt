package com.module.coreapps.serializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.util.*

class CustomDateDeserializer : JsonDeserializer<Date?> {
    override fun deserialize(
        date: JsonElement,
        typeOfSrc: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        try {
            return Date(date.asLong)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }
}