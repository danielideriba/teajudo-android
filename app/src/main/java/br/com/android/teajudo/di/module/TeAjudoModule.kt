package br.com.android.teajudo.di.module

import br.android.teajudo.di.module.ActivityModule
import br.android.teajudo.di.module.FragmentModule
import br.android.teajudo.di.module.ViewModelModule
import br.com.android.teajudo.data.remote.StoreApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        ActivityModule::class,
        FragmentModule::class,
        DbModule::class,
        ViewModelModule::class
    ]
)
open class TeAjudoModule {
    @Provides
    @Singleton
    open fun provideStoreApi(
        @Named("networkmodule") retrofit: Retrofit
    ): StoreApi = retrofit.create(StoreApi::class.java)

}