package com.module.coreapps.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.TextView

object SharedUtils {
    fun whatsappRedirect(context: Context, button: TextView, phone: String) {
        button.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${phone}"
            val intent =
                Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }
}