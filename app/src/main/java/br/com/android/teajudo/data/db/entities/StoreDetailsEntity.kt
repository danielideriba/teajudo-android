package br.com.android.teajudo.data.db.entities

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.module.coreapps.base.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = StoreDetailsEntity.TABLE_NAME,
    primaryKeys = ["idStoreDetails"]
)
@Parcelize
data class StoreDetailsEntity(
    @SerializedName("idStoreDetails") val idStoreDetails: Int? = 0,
    @SerializedName("owner") val owner: String? = "",
    @SerializedName("veracidade") val veracidade: Boolean? = false,
    @SerializedName("marketGarden") val marketGarden: Boolean? = false,
    @SerializedName("food") val food: Boolean? = false,
    @SerializedName("talk") val talk: Boolean? = false,
    @SerializedName("market") val market: Boolean? = false,
    @SerializedName("health") val health: Boolean? = false,
    @SerializedName("others") val others: String? = "",
    @SerializedName("dog") val dog: Boolean? = false
) : BaseEntity(), Parcelable {
    companion object {
        const val TABLE_NAME = "StoreDetails"
    }

    override fun getTableName(): String = TABLE_NAME
}