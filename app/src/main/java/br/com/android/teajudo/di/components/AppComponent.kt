package br.com.android.teajudo.di.components

import android.app.Application
import br.android.teajudo.di.module.*
import br.com.android.teajudo.App
import com.module.verifyconnectivitymodule.di.module.VerifyConnectivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        VerifyConnectivityModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}