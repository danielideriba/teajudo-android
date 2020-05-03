package br.android.iddog.di.module

import br.com.android.teajudo.data.MapsRepository
import br.com.android.teajudo.data.remote.MapsAPIService
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module(includes = [
    NetModule::class,
    AppModule::class
])

class RepositoryModule {
    @Provides
    @Singleton
    fun provideAPIRepository(
        apiService: MapsAPIService,
        executor: Executor
    ): MapsRepository {
        return MapsRepository(apiService, executor)
    }
}