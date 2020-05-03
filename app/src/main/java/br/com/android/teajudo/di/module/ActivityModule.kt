package br.android.iddog.di.module

import br.com.android.teajudo.ui.maps.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by danielideriba on 08,February,2019
 * TODO: All activity with injection must be declared here
 * Ex:. @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
 *      internal abstract fun contribute[NAME_ACTIVITY](): [NAME_ACTIVITY]
 */

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeMapsActivity(): MapsActivity
}