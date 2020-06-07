package com.module.coreapps.utils

object DoubleUtils {
    fun convertStringToDouble(doubleVal : String): Double{
        var doubleToReturn = 0.0
        doubleVal.toDoubleOrNull().let { lagIt ->
            if (lagIt != null) {
                doubleToReturn = lagIt.toDouble()
            }
        }

        return doubleToReturn
    }
}