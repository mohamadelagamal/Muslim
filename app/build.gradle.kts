import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // navigation safe args plugin
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    //kapt plugin
    id("kotlin-kapt")
}

android {
    namespace = "com.example.muslim"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.muslim"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // add lifecycle dependencies here
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    // add gson dependencies here to parse json
    implementation ("com.google.code.gson:gson:2.10.1")
    // add navigation components dependencies here
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

    // shared preferences
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.airbnb.android:lottie:4.2.0")// to use lottie animation in the app
    // swipe refresh layout dependency
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    // coroutines dependencies here
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // room dependencies here to use room database
    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")
    implementation("androidx.room:room-ktx:2.3.0")




}