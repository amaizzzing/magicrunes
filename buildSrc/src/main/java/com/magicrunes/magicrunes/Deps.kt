import java.util.*

private const val kotlinVersion = "1.7.0"
private const val kotlinCoreCtxVersion = "1.8.0"
private const val androidGradleVersion = "7.2.1"

//support libs
private const val appcompatVersion = "1.4.2"
private const val constraintLayoutVersion = "2.1.4"
private const val materialVersion = "1.6.1"
private const val fragmentVersion = "1.5.0"
private const val coordinatorLayoutVersion = "1.2.0"

//test libs
private const val junitVersion = "4.12"
private const val runnerVersion = "1.1.2"
private const val espressoVersion = "3.3.0"

//retrofit
private const val retrofitVersion = "2.9.0"

//room
private const val roomVersion = "2.4.2"
private const val roomCompilerVersion = "2.3.0-rc01"

//dagger
private const val daggerVersion = "2.43"

//glide
private const val glideVersion = "4.13.2"

//firebase
private const val firebaseFuncs = "20.0.0"
private const val firebaseMsg = "22.0.0"
private const val firebaseAuthVersion = "21.0.6"

//googlePlay
private const val playAds = "20.2.0"
private const val googleSrvcs = "4.3.8"

//lifecycle
private const val lifecycle_version = "2.5.0"

//coroutines
private const val coroutines_android_version = "1.6.4"
private const val coroutines_play_services_version = "1.6.4"

//navigationAndroid
private const val navigation_fragment_version = "2.5.0"

//jodaTime
private const val joda_time_version = "2.10.14"

//worker service
private const val worker_version = "2.7.1"

//compose
const val compose_version = "0.1.0-dev14"
private const val compose_compiler_version = "1.2.0"
private const val compose_activity_version = "1.5.0"
private const val compose_lifecycle_version = "2.5.0"
private const val compose_navigation_version = "2.5.0"

object Dependencies{
    object Android {
        const val minSdkVersion = 21
        const val targetSdkVersion = 31
        const val compileSdkVersion = 31
        const val applicationId = "com.magicrunes.magicrunes"
        const val versionCode = 1
        const val versionName = "1.0.0"
    }
    object Kotlin{
        const val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        const val kotlin_stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val kotlinCoreKtx = "androidx.core:core-ktx:$kotlinCoreCtxVersion"
    }

    object BuildPlugins {
        const val androidGradle = "com.android.tools.build:gradle:$androidGradleVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val googleServices = "com.google.gms:google-services:$googleSrvcs"
    }
    object SupportLibs {
        const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:$coordinatorLayoutVersion"
        const val material = "com.google.android.material:material:$materialVersion"
        const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
    }
    object TestLibs {
        const val junit = "junit:junit:$junitVersion"
        const val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"
        const val runner = "androidx.test:runner:$runnerVersion"
    }
    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val retrofitAdapterRxJava = "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"
    }
    object Room {
        const val room = "androidx.room:room-runtime:$roomVersion"
        const val room_ktx = "androidx.room:room-ktx:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    }
    object Dagger {
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$daggerVersion"
        const val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    }
    object Glide {
        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    }
    object Firebase {
        const val firebaseFunctions = "com.google.firebase:firebase-functions:$firebaseFuncs"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging:$firebaseMsg"
        const val firebaseAuth = "com.google.firebase:firebase-auth-ktx:$firebaseAuthVersion"
    }
    object GooglePlay {
        const val playServicesAds = "com.google.android.gms:play-services-ads:$playAds"
    }
    object LifeCycle {
        const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
        const val lifeCycleExtensions = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    }
    object Coroutines {
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_android_version"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_android_version"
        const val coroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutines_play_services_version"
    }
    object AndroidNavigation {
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigation_fragment_version"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigation_fragment_version"
    }
    object JodaTime {
        const val jodaTime = "joda-time:joda-time:$joda_time_version"
    }
    object Worker {
        const val workRuntimeKtx = "androidx.work:work-runtime-ktx:$worker_version"
        const val workGcm = "androidx.work:work-gcm:$worker_version"
        const val workMultiprocess = "androidx.work:work-multiprocess:$worker_version"
    }
    object Compose {
        const val compose = "androidx.compose:compose-runtime:$compose_version"
        const val composeCompiler = "androidx.compose.compiler:compiler:$compose_compiler_version"
        const val composeActivity = "androidx.activity:activity-compose:$compose_activity_version"
        const val composeLifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:$compose_lifecycle_version"
        const val composeNavigation = "androidx.navigation:navigation-compose:$compose_navigation_version"
        const val compose_version_obj = compose_compiler_version
    }

    @JvmStatic
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase(Locale.ROOT).contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}