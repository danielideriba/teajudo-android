package br.com.android.teajudo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.android.teajudo.data.db.entities.AvailableEntity
import com.module.coreapps.base.BaseDao

@Dao
interface AvailableDao : BaseDao<AvailableEntity> {
    @Query("SELECT * FROM Available WHERE idAvailable = :id")
    fun getStoreAvailable(
        id: Int
    ): LiveData<List<AvailableEntity>>
}