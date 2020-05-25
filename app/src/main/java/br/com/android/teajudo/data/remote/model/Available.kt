package br.com.android.teajudo.data.remote.model

data class Available(
    val delivery: Boolean = false,
    val addOthers: Boolean = false,
    val others: String = ""
)