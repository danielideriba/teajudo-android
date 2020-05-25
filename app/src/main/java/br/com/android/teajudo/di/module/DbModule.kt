package br.com.android.teajudo.di.module

import android.content.Context
import androidx.room.Room
import br.com.android.teajudo.data.db.TeAjudoDatabase
import br.com.android.teajudo.data.db.dao.AvailableDao
import br.com.android.teajudo.data.db.dao.StoreDao
import br.com.android.teajudo.data.db.dao.StoreDetailsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): TeAjudoDatabase {
        return Room
            .databaseBuilder(
                context,
                TeAjudoDatabase::class.java, "teajudo-db"
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStoreDao(database: TeAjudoDatabase): StoreDao {
        return database.storeDao()
    }

    @Provides
    @Singleton
    fun provideStoreDetailsDao(database: TeAjudoDatabase): StoreDetailsDao {
        return database.StoreDetailsDao()
    }

    @Provides
    @Singleton
    fun provideAvailableDao(database: TeAjudoDatabase): AvailableDao {
        return database.AvailableDao()
    }
}