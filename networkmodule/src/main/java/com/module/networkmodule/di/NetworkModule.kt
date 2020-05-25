package com.module.networkmodule.di

import android.content.Context
import android.os.Environment
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.module.coreapps.api.LiveDataCallAdapterFactory
import com.module.networkmodule.INetworkModuleConfiguration
import com.module.networkmodule.NetworkModuleConfigurations
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class NetworkModule {
    @Singleton
    @Provides
    @Named("networkmodule_cached")
    open fun provideNetworkOkHttpClient(
        networkModuleConfigurations: INetworkModuleConfiguration,
        gson: Gson,
        context: Context
    ): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @Named("networkmodule")
    open fun provideNetworkModuleRetrofit(
        gson: Gson,
        @Named("networkmodule_cached") client: OkHttpClient,
        networkModuleConfigurations: INetworkModuleConfiguration
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(networkModuleConfigurations.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

    @Singleton
    @Provides
    open fun providesNetworkModuleConfigurations(context: Context): INetworkModuleConfiguration = NetworkModuleConfigurations(context)

}