package br.com.android.teajudo.data.remote.model

import com.google.gson.annotations.SerializedName

data class StoreDetails(
    @SerializedName("owner") val owner: String? = "",
    @SerializedName("veracidade") val veracidade: Boolean? = false,
    @SerializedName("marketGarden") val marketGarden: Boolean? = false,
    @SerializedName("addOthers") val addOthers: Boolean? = false,
    @SerializedName("available") var available: Available? = null,
    @SerializedName("others") val others: String? = "",
    @SerializedName("food") val food: Boolean? = false,
    @SerializedName("talk") val talk: Boolean? = false,
    @SerializedName("health") val health: Boolean? = false,
    @SerializedName("market") val market: Boolean? = false,
    @SerializedName("dog") val dog: Boolean? = false
)
