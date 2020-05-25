package br.com.grupofleury.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("DefaultLocale")
fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

fun String.formatStringDate(
    outputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    outputLocale: Locale = LocaleUtils.PT_BR,
    inputLocale: Locale = LocaleUtils.PT_BR
): String {
    var format =
        if (inputFormat.isNotEmpty()) SimpleDateFormat(
            inputFormat,
            inputLocale
        ) else SimpleDateFormat.getDateTimeInstance()

    val newDate = format.parse(this)

    format = SimpleDateFormat(outputFormat, outputLocale)

    return if (newDate != null) format.format(newDate) else ""
}

fun String.formatStringDateOrNull(
    outputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    outputLocale: Locale = LocaleUtils.PT_BR,
    inputLocale: Locale = LocaleUtils.PT_BR
): String? {
    val result = this.formatStringDate(outputFormat, inputFormat, outputLocale, inputLocale)
    return if (result.isEmpty()) null else result
}

fun String?.toDate(
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    inputLocale: Locale = LocaleUtils.PT_BR
): Date? {
    return try {
        if (this.isNullOrEmpty()) return null

        val format =
            if (inputFormat.isNotEmpty()) SimpleDateFormat(
                inputFormat,
                inputLocale
            ) else SimpleDateFormat.getDateTimeInstance()

        format.parse(this)
    } catch (e: Exception) {
        null
    }
}