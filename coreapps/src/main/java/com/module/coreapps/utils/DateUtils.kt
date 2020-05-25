package br.com.grupofleury.core.utils

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(
    outputFormat: String = LocaleUtils.ISO_FORMAT,
    outputLocale: Locale = LocaleUtils.PT_BR
): String {
    val format = SimpleDateFormat(outputFormat, outputLocale)

    return format.format(this)
}

object DateUtils {

    fun getCurrentDate(
        outputFormat: String = LocaleUtils.ISO_FORMAT,
        outputLocale: Locale = LocaleUtils.PT_BR
    ): String {
        val c: Date = Calendar.getInstance().time

        val df = SimpleDateFormat(outputFormat, outputLocale)
        val formattedDate = df.format(c)

        return formattedDate
    }

    /**
     * d1 could be lastDateSynch
     * d2 could be the current date
     */
    fun getDifferenceBetweenDates(
        d1: String, d2: String, inputFormat: String = LocaleUtils.ISO_FORMAT,
        inputLocale: Locale = LocaleUtils.PT_BR
    ): Long {
        val df = SimpleDateFormat(inputFormat, inputLocale)

        try {
            val date1 = df.parse(d1)
            val date2 = df.parse(d2)

            //calculate difference

            val difference = (date2?.time ?: 0) - (date1?.time ?: 0)

            val secondsInMilli = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            return difference / daysInMilli

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0L
    }

}