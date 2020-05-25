package com.module.networkmodule

import android.content.Context
import com.module.networkmodule.exceptions.InvalidConfigurationException

open class NetworkModuleConfigurations(val context: Context) :
    INetworkModuleConfiguration {

    init {
        if (BuildConfig.BASE_URL.isBlank())
            throw InvalidConfigurationException(
                context.getString(
                    R.string.invalidConfigurationException,
                    "sensediaBaseUrl"
                )
            )
    }

    override var baseUrl: String
        get() = BuildConfig.BASE_URL
        set(@Suppress("UNUSED_PARAMETER") value) {}
}