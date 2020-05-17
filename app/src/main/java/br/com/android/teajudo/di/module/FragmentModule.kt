package br.android.teajudo.di.module

import android.webkit.PermissionRequest
import br.com.android.teajudo.ui.maps.MapsFragment
import br.com.android.teajudo.ui.permissionRequests.PermissionRequestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by danielideriba on 08,February,2019
 * TODO: All fragment with injection must be declared here
 * Ex:. @ContributesAndroidInjector
 *      abstract fun contribute[NAME_FRAGMENT](): [NAME_FRAGMENT]
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePermissionRequestFragment(): PermissionRequestFragment

    @ContributesAndroidInjector
    abstract fun contributeMapsFragment(): MapsFragment
}