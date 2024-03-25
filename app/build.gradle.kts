plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.okifirsyah.githubmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.okifirsyah.githubmate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        buildConfigField(
            "String", "TOKEN",
            "MY_TOKEN"
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // Preferences DataStore (SharedPreferences like APIs)
    implementation(libs.androidx.datastore.preferences)

    //  Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //  Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //  ViewPager
    implementation(libs.androidx.viewpager2)

    //  Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    //  Timber
    implementation(libs.timber)

    //  okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //  Retrofit
    implementation(libs.converter.gson)
    implementation(libs.retrofit)

    // Image View
    implementation(libs.circleimageview)
    implementation(libs.roundedimageview)

    // Coil
    implementation(libs.coil)

    //Shimmering
    implementation(libs.shimmer)
}
