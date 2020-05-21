import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

val baseUrlDev: String by project
val baseUrlPrd: String by project

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
    id(BuildPlugins.navigationSafeArgs)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "br.com.android.teajudo"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        buildConfigFields
    }
    kapt {
        correctErrorTypes = false
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("api")

    productFlavors {
        create("dev") {
            buildConfigField("String", "API_URL", "\"$baseUrlDev\"")
            setDimension("api")
        }
        create("prd") {
            buildConfigField("String", "API_URL", "\"$baseUrlPrd\"")
            setDimension("api")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        val options = this as KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libraries.KOTLINSTDLIB)
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.KTXCORE)
    implementation(Libraries.VIEWPAGERX)
    implementation(Libraries.MATERIAL)
    implementation(Libraries.CONSTRAINTLAYOUT)
    implementation(Libraries.ANDROID_LIFECYCLE_EX)
    implementation(Libraries.ANDROID_LIFECYCLE_LIVEDATA)
    implementation(Libraries.ANDROID_LIFECYCLE_RUNTIME)
    implementation(Libraries.ANDROID_LIFECYCLE_VIEWMODEL)
    implementation(Libraries.OKHTTP_LOGGER)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.GLIDE)
    implementation(Libraries.GLIDE_OKHTTP3)
    implementation(Libraries.TIMBER)

    implementation(Libraries.FIREBASE_DATABASE)
    implementation(Libraries.FIREBASE_AUTH)

    implementation(Libraries.STETHO)
    implementation(Libraries.STETHO_OKHTTP)
    implementation(Libraries.RXANDROID)
    implementation(Libraries.RXJAVA)
    implementation(Libraries.RXJAVA_ADAPTER)
    implementation(Libraries.ANDROIDX_LEGACY)

    implementation(Libraries.DAGGER)
    kapt(Libraries.DAGGER_ANDROID_PROCESSOR)
    kapt(Libraries.DAGGER_COMPILER)
    implementation(Libraries.DAGGER_ANDROID)

    implementation(Libraries.MULTIDEX)

    implementation(Libraries.GMAPS)
    implementation(Libraries.DEXTER)

    implementation(Libraries.LOTTIE)

    //Custom Libraries
    implementation(Libraries.CONNECTIVITY_MODULE)

    //JetPack
    implementation(Libraries.JETPACK_NAVIGATION)
    implementation(Libraries.JETPACK_NAVIGATION_UI)
    implementation(Libraries.JETPACK_FRAGMENT_NAVIGATION)

    annotationProcessor(Libraries.DAGGER_ANDROID_PROCESSOR)
    annotationProcessor(Libraries.DAGGER_COMPILER)
    annotationProcessor(Libraries.GLIDE_ANNOTATION)

    testImplementation(TestLibraries.JUNIT4)
    testImplementation(TestLibraries.MOCKK)
    androidTestImplementation(TestLibraries.TESTRUNNER)
    androidTestImplementation(TestLibraries.ESPRESSO)
    androidTestImplementation(TestLibraries.JETPACK_NAVIGATION_TEST)
}

//apply(mapOf("plugin" to "com.google.gms.google-services"))

