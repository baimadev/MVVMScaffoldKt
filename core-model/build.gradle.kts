import com.zlingsmart.mvvmscaffold.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.ksp.get().pluginId) version libs.versions.ksp.get()
}


android {
    compileSdk = Configuration.compileSdk
    defaultConfig{
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
    }

}

dependencies {
    // json parsing
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    // logger
    api(libs.timber)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.arch.core)
}