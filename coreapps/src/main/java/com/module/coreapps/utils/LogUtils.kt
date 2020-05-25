package br.com.grupofleury.core.utils

import android.util.Log
import com.module.coreapps.BuildConfig
import timber.log.Timber

@Suppress("unused")
object LogUtils {
    class InitTimber {
        init {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }
        }
    }

    /** A tree which logs important information for crash reporting. */
    class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }

    }
}