package br.com.android.teajudo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.android.teajudo.data.db.dao.AvailableDao
import br.com.android.teajudo.data.db.dao.StoreDao
import br.com.android.teajudo.data.db.dao.StoreDetailsDao
import br.com.android.teajudo.data.db.entities.AvailableEntity
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import com.module.coreapps.converters.CoreRoomConverters

@Database(
    entities = [
        StoreEntity::class,
        StoreDetailsEntity::class,
        AvailableEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(CoreRoomConverters::class, TeAjudoRoomConverters::class)
abstract class TeAjudoDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao

    abstract fun StoreDetailsDao(): StoreDetailsDao

    abstract fun AvailableDao(): AvailableDao
}