package br.com.android.teajudo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import com.module.coreapps.base.BaseDao

@Dao
interface StoreDao : BaseDao<StoreEntity> {
    @Query("SELECT * FROM Store")
    fun getStores(): LiveData<List<StoreEntity>>
}