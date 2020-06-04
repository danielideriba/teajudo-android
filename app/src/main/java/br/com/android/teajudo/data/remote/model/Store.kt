package br.com.android.teajudo.data.remote.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("whatsapp") val whatsapp: Int = 0,
    @SerializedName("lng") val lng: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("options") val options: StoreDetails,
    @SerializedName("type") val type: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("lat") val lat: String = ""
)