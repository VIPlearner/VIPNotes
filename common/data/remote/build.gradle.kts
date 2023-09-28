@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.viplearner.common.data.remote"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":common:domain"))
    implementation(libs.activity)
    implementation(libs.jakewharton.timber)
    implementation(libs.play.services.auth)
    implementation(libs.core.ktx)
    implementation(libs.firebase.bom)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt)
    implementation(libs.junit4)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)
    kapt(libs.hilt.compiler)
}