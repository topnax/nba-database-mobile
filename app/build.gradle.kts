import com.android.build.api.dsl.ApplicationBuildType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.github.topnax.nbadatabasemobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.topnax.nbadatabasemobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            addBalldontlieApiKeyFromEnv()
        }
        release {
            addBalldontlieApiKeyFromEnv()

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
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.koin.core.viewmodel.navigation)
    implementation(libs.koin.androidx.compose)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.pagingRuntime)
    implementation(libs.pagingCompose)
    implementation(project(":data"))
    implementation(libs.timber)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(project(":data"))
    implementation(project(":implementation"))
    implementation(project(":balldontlieapi"))
    implementation(project(":domain"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

private fun ApplicationBuildType.addBalldontlieApiKeyFromEnv() {
    val apiKey = System.getenv("BALLDONTLIE_API_KEY") // TODO raise exception if not set (but don't fail during IDE sync)
    buildConfigField("String", "BALLDONTLIE_API_KEY", "\"$apiKey\"")
}