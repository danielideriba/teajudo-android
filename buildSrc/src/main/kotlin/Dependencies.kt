import org.gradle.api.artifacts.dsl.DependencyHandler

const val kotlinVersion = "1.3.20"

object BuildPlugins {
    const val androidGradlePlugin       = "com.android.tools.build:gradle:${Versions.BUILDTOOLVERSION}"
    const val kotlinGradlePlugin        = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication        = "com.android.application"
    const val kotlinAndroid             = "kotlin-android"
    const val kotlinAndroidExtensions   = "kotlin-android-extensions"
    const val kapt                      = "kotlin-kapt"
    const val navigationSafeArgs        = "androidx.navigation.safeargs.kotlin"
    const val firebaseService           = "com.google.gms:google-services:${Versions.Firebase.SERVICE}"
}

object AndroidSdk {
    const val min = 16
    const val compile = 28
    const val target = compile
}

object Libraries {
    const val KOTLINSTDLIB                  = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val APPCOMPAT                     = "androidx.appcompat:appcompat:${Versions.Androidx.APP_COMPAT}"
    const val CONSTRAINTLAYOUT              = "androidx.constraintlayout:constraintlayout:${Versions.Androidx.CONSTRAINTLAYOUT}"
    const val KTXCORE                       = "androidx.core:core-ktx:${Versions.Androidx.CORE_KTX}"
    const val VIEWPAGERX                    = "androidx.viewpager2:viewpager2:${Versions.Androidx.VIEWPAGERX}"
    const val MATERIAL                      = "com.google.android.material:material:${Versions.MATERIAL}"
    const val OKHTTP_LOGGER                 = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"
    const val DAGGER                        = "com.google.dagger:dagger:${Versions.DAGGER2}"
    const val DAGGER_ANDROID                = "com.google.dagger:dagger-android-support:${Versions.DAGGER2_SUPPORT}"
    const val DAGGER_COMPILER               = "com.google.dagger:dagger-compiler:${Versions.DAGGER2}"
    const val DAGGER_ANDROID_PROCESSOR      = "com.google.dagger:dagger-android-processor:${Versions.DAGGER2}"
    const val FIREBASE_CORE                 = "com.google.firebase:firebase-core:${Versions.Firebase.CORE}"
    const val FIREBASE_AUTH                 = "com.google.firebase:firebase-auth:${Versions.Firebase.AUTH}"
    const val FIREBASE_DATABASE             = "com.google.firebase:firebase-database:${Versions.Firebase.DATABASE}"
    const val RETROFIT                      = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON       = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val ANDROID_LIFECYCLE_RUNTIME     = "androidx.lifecycle:lifecycle-runtime:${Versions.Androidx.LIFECYCLE}"
    const val ANDROID_LIFECYCLE_VIEWMODEL   = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Androidx.LIFECYCLE}"
    const val ANDROID_LIFECYCLE_EX          = "androidx.lifecycle:lifecycle-extensions:${Versions.Androidx.LIFECYCLE}"
    const val ANDROID_LIFECYCLE_LIVEDATA    = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Androidx.LIFECYCLE}"
    const val STETHO                        = "com.facebook.stetho:stetho:${Versions.STETHO}"
    const val STETHO_OKHTTP                 = "com.facebook.stetho:stetho-okhttp3:${Versions.STETHO}"
    const val GLIDE                         = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_ANNOTATION              = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val GLIDE_OKHTTP3                 = "com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE}"
    const val TIMBER                        = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val RXJAVA                        = "io.reactivex.rxjava2:rxjava:${Versions.RX.RXJAVA}"
    const val RXANDROID                     = "io.reactivex.rxjava2:rxandroid:${Versions.RX.RXANDROID}"
    const val RXJAVA_ADAPTER                = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RX.RXJAVA_ADAPTER}"
    const val MULTIDEX                      = "com.android.support:multidex:${Versions.MULTIDEX}"
    const val ANDROIDX_LEGACY               = "androidx.legacy:legacy-support-v4:${Versions.ANDROIDX_LEGACY}"
    const val GMAPS                         = "com.google.android.gms:play-services-maps:${Versions.GoogleMaps.MAPS}"
    const val GMAPS_UTILS                   = "com.google.maps.android:android-maps-utils:${Versions.GoogleMaps.MAPS_UTIL}"
    const val GMAPS_UTILS_V3                = "com.google.maps.android:android-maps-utils-v3:${Versions.GoogleMaps.MAPS_UTIL}"
    const val DEXTER                        = "com.karumi:dexter:${Versions.DEXTER}"
    const val LOTTIE                        = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val CONNECTIVITY_MODULE           = "com.github.danielideriba:VerifyConnectivityModule:${Versions.CONNECTIVITY_MODULE}"

    const val JETPACK_NAVIGATION            = "android.arch.navigation:navigation-fragment-ktx:${Versions.Jetpack.JETPACK_NAVIGATION}"
    const val JETPACK_NAVIGATION_UI         = "androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.JETPACK_NAVIGATION}"
    const val JETPACK_FRAGMENT_NAVIGATION   = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.Jetpack.JETPACK_NAVIGATION}"

    const val DATABINDING                   = "com.android.databinding:compiler:${Versions.Jetpack.DATABIND}"
    const val LOCATION_SERVICES             = "com.google.android.gms:play-services-location:${Versions.GoogleMaps.LOCATION_SERVICES}"

    //Room
    const val ROOM_RUNTIME                  = "androidx.room:room-runtime:${Versions.Room.ROOM}"
    const val ROOM_COMPILER_KAPT            = "androidx.room:room-compiler:${Versions.Room.ROOM}"
    const val ROOM_KTX                      = "androidx.room:room-ktx:${Versions.Room.ROOM}"
    const val ROOM_RXJAVA                   = "androidx.room:room-rxjava2:${Versions.Room.ROOM}"

    //arch-comp Room
    const val ARCH_EXTENSION                = "android.arch.lifecycle:extensions:${Versions.Room.ROOM_ARCH}"
    const val ARCH_COMMON                   = "android.arch.lifecycle:common-java8:${Versions.Room.ROOM_ARCH}"
    const val ARCH_PERSISTENCE              = "android.arch.persistence.room:runtime:${Versions.Room.ROOM_ARCH}"
    const val ARCH_PERSISTENCE_KAPT         = "android.arch.persistence.room:compiler:${Versions.Room.ROOM_ARCH}"
}

object TestLibraries {
    const val JUNIT4                        = "junit:junit:${Versions.Tests.JUNIT4}"
    const val TESTRUNNER                    = "androidx.test:runner:${Versions.Tests.TESTRUNNER}"
    const val ESPRESSO                      = "androidx.test.espresso:espresso-core:${Versions.Tests.ESPRESSO}"
    const val MOCKK                         = "io.mockk:mockk:${Versions.Tests.MOCKK}"
    const val JETPACK_NAVIGATION_TEST       = "androidx.navigation:navigation-testing:${Versions.Tests.JETPACK_NAVIGATION_TEST}"
}