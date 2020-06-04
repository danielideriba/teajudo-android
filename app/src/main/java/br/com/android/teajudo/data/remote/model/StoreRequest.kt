package br.com.android.teajudo.data.remote.model

import com.google.gson.annotations.SerializedName

data class StoreRequest(
    @SerializedName("data") val data: List<Store>,
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: Boolean = false
)