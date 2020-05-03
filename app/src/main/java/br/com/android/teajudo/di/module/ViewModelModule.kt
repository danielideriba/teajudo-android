package br.android.iddog.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.di.key.ViewModelKey
import br.com.android.teajudo.ui.maps.MapsViewModel
import br.com.android.teajudo.utils.FactoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMainViewModel(repoViewModel: MapsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory
}