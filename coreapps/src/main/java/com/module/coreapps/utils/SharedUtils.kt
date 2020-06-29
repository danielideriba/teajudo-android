package com.module.coreapps.utils

import android.app.Activity
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

    fun emailRedirect(activity: Activity, button: TextView, email: String, emailStrings: Array<String>){
        if(emailStrings.size == 4) {
            button.setOnClickListener {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null
                    )
                )

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailStrings[0])
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailStrings[1])
                activity.startActivity(Intent.createChooser(emailIntent, emailStrings[3]))
            }
        }
    }

    fun phoneRedirect(activity: Activity, button: TextView, number: String) {
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number.trim())))
            activity.startActivity(intent)
        }
    }
}