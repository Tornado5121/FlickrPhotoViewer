plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.zhadko.loremflickrpictureviewer"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

}


dependencies {

    // Android
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.androidx.core)
    implementation(libs.androidx.legacy)
    implementation(libs.android.material)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)

    //Retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)

    //Room
    implementation(libs.room)
    kapt(libs.room.kapt)

    //Coroutines
    implementation(libs.kotlinx.coroutines)

    //Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)

    //Glide
    implementation(libs.bumptech.glide)

}