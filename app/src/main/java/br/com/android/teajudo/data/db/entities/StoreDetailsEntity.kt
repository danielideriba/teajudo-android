package br.com.android.teajudo.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.module.coreapps.base.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = StoreDetailsEntity.TABLE_NAME
)
@Parcelize
data class StoreDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("owner") val owner: String,
    @SerializedName("veracidade") val veracidade: Boolean,
    @SerializedName("market_garden") val marketGarden: Boolean
) : BaseEntity(), Parcelable {
    companion object {
        const val TABLE_NAME = "StoreDetails"
    }

    override fun getTableName(): String = TABLE_NAME
}