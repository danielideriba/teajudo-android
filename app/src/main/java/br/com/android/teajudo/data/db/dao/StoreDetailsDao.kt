package br.com.android.teajudo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import com.module.coreapps.base.BaseDao

@Dao
interface StoreDetailsDao : BaseDao<StoreDetailsEntity> {
    @Query("SELECT * FROM StoreDetails WHERE idStoreDetails = :id")
    fun getStoreById(
        id: Int
    ): LiveData<List<StoreDetailsEntity>>
}