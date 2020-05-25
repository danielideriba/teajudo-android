package br.android.teajudo.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context =
        application.applicationContext
}