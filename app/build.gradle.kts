plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.booktracker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.booktracker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    dependencies {
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.10")

        // Jetpack Compose
        implementation("androidx.activity:activity-compose:1.9.0")
        implementation("androidx.compose.ui:ui:1.8.3")
        implementation("androidx.compose.material3:material3:1.3.2")
        implementation("androidx.compose.ui:ui-tooling-preview:1.8.3")
        debugImplementation("androidx.compose.ui:ui-tooling:1.8.3")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

        // Navigation
        implementation("androidx.navigation:navigation-compose:2.9.2")


        // Room (with coroutines)
        implementation("androidx.room:room-runtime:2.7.2")
        ksp("androidx.room:room-compiler:2.7.2")
        implementation("androidx.room:room-ktx:2.7.2")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

        // Coil (image loading)
        implementation("io.coil-kt:coil-compose:2.7.0")

        implementation("androidx.compose.material:material-icons-extended:1.7.8")

        implementation("androidx.compose.material:material-icons-extended:1.6.5")
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        implementation("androidx.compose.material3:material3:1.3.2")
        implementation("androidx.compose.material:material-icons-extended:1.7.8")

        // For image loading
        implementation("io.coil-kt:coil-compose:2.7.0")

// For image picker
        implementation("androidx.activity:activity-compose:1.7.2")

    }

}

