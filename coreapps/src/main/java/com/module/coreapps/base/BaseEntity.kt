package com.module.coreapps.base

import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.joda.time.DateTimeComparator
import java.util.*

abstract class BaseEntity {
    init {
        syncDates()
    }

    abstract fun getTableName(): String

    open val shouldFetch: Boolean
        get() = isOldData

    @Ignore
    @ColumnInfo(name = "firstSync")
    var firstSync: Date? = null

    @Ignore
    @ColumnInfo(name = "lastSync")
    var lastSync: Date? = null

    val isOldData: Boolean
        get() {
            val dateTimeComparator =
                DateTimeComparator.getDateOnlyInstance()
            var isOldData = lastSync == null
            lastSync?.let {
                val compareResult = dateTimeComparator.compare(
                    lastSync,
                    Calendar.getInstance().time
                )
                isOldData = compareResult < 0
            }
            return isOldData
        }

    private fun syncDates() {
        val now = Calendar.getInstance().time
        if (firstSync == null) {
            firstSync = now
        }
        lastSync = now
    }
}

fun <T : BaseEntity> T?.shouldFetch(): Boolean {
    return this?.shouldFetch ?: true
}

fun <T : BaseEntity> Collection<T>?.shouldFetch(): Boolean {
    return (this?.any { it.shouldFetch } ?: true || this?.size == 0)
}