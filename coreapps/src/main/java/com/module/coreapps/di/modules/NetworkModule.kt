package com.module.coreapps.di.modules

import android.content.Context
import android.os.Environment
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.module.coreapps.base.NetworkHelper
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class NetworkModule {
    @Singleton
    @Provides
    @Named("cached")
    open fun provideOkHttpClient(): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    open fun provideNonCachedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    open fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        gsonBuilder.registerTypeAdapter(Date::class.java, CustomDateSerializer())
//        gsonBuilder.registerTypeAdapter(Date::class.java, CustomDateDeserializer())
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    open fun provideNetworkHelper(context: Context): NetworkHelper = NetworkHelper(context)
}