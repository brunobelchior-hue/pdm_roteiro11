plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.example.sgm"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.sgm"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "3.0"
    }
    buildFeatures { viewBinding = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.fragment:fragment:1.8.9")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.9.2")
    implementation("androidx.room:room-runtime:2.7.2")
    annotationProcessor("androidx.room:room-compiler:2.7.2")
    implementation("com.google.dagger:hilt-android:2.58")
    annotationProcessor("com.google.dagger:hilt-compiler:2.58")
}
