import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(Dependencies.Android.compileSdkVersion)
    defaultConfig {
        applicationId = Dependencies.Android.applicationId
        minSdkVersion(Dependencies.Android.minSdkVersion)
        targetSdkVersion(Dependencies.Android.targetSdkVersion)
        versionCode = Dependencies.Android.versionCode
        versionName = Dependencies.Android.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        localProperties.load(rootProject.file("local.properties").inputStream())
        val googleRequestIdToken = localProperties["googleRequestIdToken"].toString()
        resValue("string", "googleRequestIdToken", googleRequestIdToken)
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Kotlin.kotlin_std)
    implementation(Dependencies.Kotlin.kotlin_stdLib)
    implementation(Dependencies.Kotlin.kotlinCoreKtx)
    implementation(Dependencies.SupportLibs.appcompat)
    implementation(Dependencies.SupportLibs.constraintLayout)
    implementation(Dependencies.SupportLibs.coordinatorLayout)
    implementation(Dependencies.SupportLibs.material)
    implementation(Dependencies.SupportLibs.fragment)
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.retrofitAdapterRxJava)
    implementation(Dependencies.Retrofit.retrofitConverterGson)
    implementation(Dependencies.Room.room)
    implementation(Dependencies.Room.room_ktx)
    implementation(Dependencies.Dagger.dagger)
    implementation(Dependencies.Dagger.daggerAndroid)
    implementation(Dependencies.Glide.glide)
    implementation(Dependencies.LifeCycle.lifeCycleViewModel)
    implementation(Dependencies.LifeCycle.lifeCycleExtensions)
    implementation(Dependencies.Coroutines.coroutinesAndroid)
    implementation(Dependencies.Coroutines.coroutinesCore)
    implementation(Dependencies.Coroutines.coroutinesPlayServices)
    implementation(Dependencies.AndroidNavigation.navigationFragment)
    implementation(Dependencies.AndroidNavigation.navigationUi)
    implementation(Dependencies.JodaTime.jodaTime)
    implementation(Dependencies.Worker.workGcm)
    implementation(Dependencies.Worker.workMultiprocess)
    implementation(Dependencies.Worker.workRuntimeKtx)
    implementation(Dependencies.Firebase.firebaseAuth)
    implementation("com.google.android.gms:play-services-auth:20.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.0.0")

    kapt(Dependencies.Room.roomCompiler)
    kapt(Dependencies.Dagger.daggerCompiler)
    kapt(Dependencies.Dagger.daggerAndroidProcessor)

    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.runner)
    androidTestImplementation(Dependencies.TestLibs.espresso)
}