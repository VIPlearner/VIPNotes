@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.junit4)
}