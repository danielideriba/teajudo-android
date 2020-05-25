package br.com.android.teajudo.utils

import android.content.Context

object ScreenUtils {
    fun getScreenHeightInDP(context: Context) : Int{
        var displayMetrics = context.resources.displayMetrics
        var dpHeight = 0

        displayMetrics?.let {
            dpHeight = it.heightPixels
        }

        return dpHeight
    }

    fun getScreenWidthInDP(context: Context) : Int{
        var displayMetrics = context.resources.displayMetrics
        var dpWidth = 0

        displayMetrics?.let {
            dpWidth = it.widthPixels
        }

        return dpWidth
    }
}