package br.com.android.teajudo.data.remote.model

import com.google.gson.annotations.SerializedName

data class Available(
    @SerializedName("delivery") val delivery: Boolean? = false,
    @SerializedName("addOthers") val addOthers: Boolean? = false,
    @SerializedName("others") val others: String? = ""
)