package br.android.iddog.di.module


import br.com.android.teajudo.BuildConfig
import br.com.android.teajudo.data.remote.MapsAPIService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideRetrofit(
        gson: Gson,
        @Named("apiurl") githubURL: String,
        okhttp: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(githubURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okhttp)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @Named("apiurl")
    fun provideAPIURL(): String {
        return BuildConfig.API_URL
    }

    @Provides
    @Singleton
    fun provideDogAPIService(restAdapter: Retrofit): MapsAPIService {
        return restAdapter.create(MapsAPIService::class.java)
    }
}
