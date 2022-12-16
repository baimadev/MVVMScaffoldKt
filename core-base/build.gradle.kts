import com.zlingsmart.mvvmscaffold.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android{
    compileSdk = Configuration.compileSdk

    defaultConfig{
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // androidx
    implementation(libs.material)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.startup)

    // coroutines
    implementation(libs.coroutines)
    testImplementation(libs.coroutines)
    testImplementation(libs.coroutines.test)

    api(libs.unpeekLiveData)

    // di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
