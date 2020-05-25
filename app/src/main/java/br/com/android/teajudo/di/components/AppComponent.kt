package br.com.android.teajudo.di.components

import android.app.Application
import br.android.teajudo.di.module.AppModule
import br.com.android.teajudo.App
import br.com.android.teajudo.di.module.TeAjudoModule
import com.module.coreapps.di.modules.CoreModule
import com.module.networkmodule.di.NetworkModule
import com.module.verifyconnectivitymodule.di.module.VerifyConnectivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        TeAjudoModule::class,
        CoreModule::class,
        NetworkModule::class,
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