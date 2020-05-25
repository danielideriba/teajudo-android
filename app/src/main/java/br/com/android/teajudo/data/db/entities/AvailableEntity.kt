package br.com.android.teajudo.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.module.coreapps.base.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = AvailableEntity.TABLE_NAME
)
@Parcelize
data class AvailableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("others") val others: String,
    @SerializedName("delivery") val delivery: Boolean,
    @SerializedName("add_others") val add_others: Boolean
) : BaseEntity(), Parcelable {
    companion object {
        const val TABLE_NAME = "Available"
    }

    override fun getTableName(): String = TABLE_NAME
}