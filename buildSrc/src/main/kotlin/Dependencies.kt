internal object Versions {

    const val shimmerLayout = "0.5.0"

    const val swipeToRefresh = "1.1.0"

    // network
    const val retrofit = "2.9.0"
    const val okHttp = "4.9.0"

    // firebase
    const val firebaseAuth = "20.0.3"
    const val firebaseDb = "19.7.0"
    const val fireStore = "22.1.2"

    //    progressBar
    const val progressBar = "2.5.0"

    const val gson = "2.8.6"
    const val dataStore = "1.0.0-alpha08"

    const val activity = "1.2.2"
    const val easyPermission = "3.0.0"
    const val googleServiceAuth = "19.0.0"
    const val circleImageView = "3.1.0"
    const val coil = "1.1.1"
    const val navigation = "2.3.4"
    const val coroutines = "1.4.1"
    const val dagger = "2.28.3"
    const val room = "2.2.6"

    const val lifecycleExtension = "2.2.0"
    const val fragment = "1.3.2"
    const val lifecycleCommon = "2.3.1"

    // Views
    const val constraintLayout = "2.0.4"

    // Tests
    const val jUnitCore = "4.13.2"
    const val jUnitTest = "1.1.2"
    const val espresso = "3.3.0"
    const val assertJ = "3.18.1"

    // Android Core
    const val kotlin_version = "1.4.31"
    const val gradle = "4.1.3"
    const val core = "1.3.2"
    const val appcompat = "1.2.0"
    const val material = "1.3.0"

    const val sdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

@SuppressWarnings("UnusedAssignment")
object Dependencies {
    const val shimmerLayout = "com.facebook.shimmer:shimmer:${Versions.shimmerLayout}"

    const val swipeToRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeToRefresh}"

    // network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // firebase
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val firebaseDb = "com.google.firebase:firebase-database:${Versions.firebaseDb}"
    const val firebaseFireStore = "com.google.firebase:firebase-firestore-ktx:${Versions.fireStore}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"


    // DataStore
    const val dataStoreCore = "androidx.datastore:datastore-core:${Versions.dataStore}"
    const val dataStorePreferences =
        "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    const val progressBar =
        "com.github.rahatarmanahmed:circularprogressview:${Versions.progressBar}"

    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"

    // EasyPermission
    const val easyPermission = "pub.devrel:easypermissions:${Versions.easyPermission}"

    // google service auth
    const val googleServiceAuth =
        "com.google.android.gms:play-services-auth:${Versions.googleServiceAuth}"

    // circularCardView
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageView}"

    // Coil
    const val coil = "io.coil-kt:coil:${Versions.coil}"

    //  Navigation
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // lifecycle
    const val lifecycleExtension =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtension}"
    const val lifecycleCommon =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleCommon}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    // room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // Dagger2
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompile = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Views
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Tests
    const val jUnitCore = "junit:junit:${Versions.jUnitCore}"
    const val jUnitTest = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"

    // Android Core
    const val kotlin_lib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val kotlin_plugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val gradleTools = "com.android.tools.build:gradle:${Versions.gradle}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
}