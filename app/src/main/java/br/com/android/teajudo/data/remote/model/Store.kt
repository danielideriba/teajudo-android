package br.com.android.teajudo.data.remote.model

data class Store(
    val whatsapp: Int = 0,
    val lng: String = "",
    val phone: String = "",
    val name: String = "",
    val options: StoreDetails,
    val type: String = "",
    val email: String = "",
    val lat: String = ""
)