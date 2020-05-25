package br.com.grupofleury.core.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import timber.log.Timber

fun CustomDialog(context: Context, title: String, msg: String) {
    val builder = AlertDialog.Builder(context)

    builder.setTitle(title)
    builder.setMessage(msg)

    builder.setPositiveButton("OK"){dialog, which ->
        Timber.d("Ok, we change the app background.")
    }
//
//    builder.setNegativeButton("No"){dialog,which ->
//        Timber.d("You are not agree.")
//    }
//
//    builder.setNeutralButton("Cancel"){_,_ ->
//        Timber.d("You cancelled the dialog.")
//    }

    val dialog: AlertDialog = builder.create()
    dialog.show()
}