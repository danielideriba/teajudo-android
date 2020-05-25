package com.module.coreapps.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class]
)
class CoreModule{
    @Singleton
    @Provides
    fun providePreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

}