package br.com.android.teajudo.data.remote.model

data class StoreRequest(
    val data: List<Store>?,
    val message: String = "",
    val status: Boolean = false
)