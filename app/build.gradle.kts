import com.zlingsmart.mvvmscaffold.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
   // id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
    id(libs.plugins.kotlin.extension.get().pluginId)
}


android {
    compileSdk = Configuration.compileSdk
    defaultConfig {
        applicationId = "com.zlingsmart.mvvmscaffold"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "com.zlingsmart.mvvmscaffold.AppTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    hilt {
        enableAggregatingTask = true
    }

    kotlin {
        sourceSets.configureEach {
            kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin/")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

}

dependencies {
    implementation(project(":core-repository"))
    implementation(project(":core-base"))

    // androidx
    implementation(libs.material)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.ext)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.kotlin.ktx)
    implementation(libs.kotlin.stdlib)

    // di
    implementation(libs.hilt.android)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.hilt.compiler)

    // coroutines
    implementation(libs.coroutines)

    // image loading
    implementation(libs.glide)
    implementation(libs.glide.palette)

    // recyclerView
    implementation(libs.recyclerview)
    implementation(libs.view.recyclerview)

    // unit test
//    testImplementation(libs.junit)
//    testImplementation(libs.turbine)
//    testImplementation(libs.androidx.test.core)
//    testImplementation(libs.mockito.kotlin)
//    testImplementation(libs.mockito.inline)
//    testImplementation(libs.coroutines.test)
//    androidTestImplementation(libs.truth)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso)
//    androidTestImplementation(libs.android.test.runner)
}
