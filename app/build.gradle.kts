plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.promecarus.aplikasigithubuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.promecarus.aplikasigithubuser"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("Boolean", "DEBUG", "true")
        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "GITHUB_API_KEY", "\"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\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0-alpha03")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    ksp("androidx.room:room-compiler:2.6.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha01")
    androidTestImplementation("androidx.test:rules:1.6.0-alpha01")
    androidTestImplementation("androidx.test:runner:1.6.0-alpha04")
}
