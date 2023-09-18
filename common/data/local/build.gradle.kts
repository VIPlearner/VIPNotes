plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.viplearner.common.data.local"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
    implementation(libs.core.ktx)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt)
    implementation(libs.localizationManager)
    kapt(libs.hilt.compiler)
    runtimeOnly(libs.room.runtime)
    implementation(libs.androidx.dataStore.preferences)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.room.testing)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.test.runner)
}