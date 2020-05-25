package com.module.coreapps.serializers

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.*

class CustomDateSerializer  : JsonSerializer<Date?> {
    override fun serialize(
        date: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(date?.time)
    }
}