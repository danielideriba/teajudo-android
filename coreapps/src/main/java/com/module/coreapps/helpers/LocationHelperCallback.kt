package com.module.coreapps.helpers

import android.location.Location

interface LocationHelperCallback {
    fun updateUi(location: Location)
}