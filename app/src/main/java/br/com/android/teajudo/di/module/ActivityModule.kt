package br.android.teajudo.di.module

import br.com.android.teajudo.di.scope.ActivityScope
import br.com.android.teajudo.ui.LauncherActivity
import br.com.android.teajudo.ui.business.BusinessActivity
import br.com.android.teajudo.ui.helpingHand.HelpingHandActivity
import br.com.android.teajudo.ui.maps.MapsActivity
import br.com.android.teajudo.ui.permissionRequests.PermissionRequestsActivity
import br.com.android.teajudo.ui.volunteers.VolunteersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by danielideriba on 08,February,2019
 * TODO: All activity with injection must be declared here
 * Ex:. @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
 *      internal abstract fun contribute[NAME_ACTIVITY](): [NAME_ACTIVITY]
 */

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeLauncherActivity(): LauncherActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributePermissionRequestsActivity(): PermissionRequestsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeMapsActivity(): MapsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeBusinessActivity(): BusinessActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeHelpingHandActivity(): HelpingHandActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun contributeHelpingVolunteersActivity(): VolunteersActivity
}