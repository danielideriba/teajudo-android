package br.com.android.teajudo.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.module.coreapps.base.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = StoreEntity.TABLE_NAME
)
@Parcelize
data class StoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("whatsapp") val whatapp: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("type") val type: String
) : BaseEntity(), Parcelable {
    companion object {
        const val TABLE_NAME = "Store"
    }

    override fun getTableName(): String = TABLE_NAME
}