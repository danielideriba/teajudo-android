package br.com.android.teajudo.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.module.coreapps.base.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = AvailableEntity.TABLE_NAME,
    primaryKeys = ["idAvailable"]
)
@Parcelize
data class AvailableEntity(
    @SerializedName("idAvailable") val idAvailable: Int? = 0,
    @SerializedName("others") val others: String? = "",
    @SerializedName("delivery") val delivery: Boolean? = false,
    @SerializedName("add_others") val add_others: Boolean? = false
) : BaseEntity(), Parcelable {

    companion object {
        const val TABLE_NAME = "Available"
    }

    override fun getTableName(): String = TABLE_NAME
}