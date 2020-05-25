package br.com.android.teajudo.data.remote.model

data class StoreDetails(
    val owner: String = "",
    val veracidade: Boolean = false,
    val marketGarden: Boolean = false,
    val addOthers: Boolean = false,
    val available: Available,
    val others: String = ""
)