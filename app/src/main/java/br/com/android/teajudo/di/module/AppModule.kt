package br.android.iddog.di.module

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class AppModule {
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }
}