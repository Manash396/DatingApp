import com.android.build.api.dsl.ApplicationExtension
import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

extensions.configure<ApplicationExtension> {

    namespace = "com.mk.datingapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.mk.datingapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val googleClientId = localProperties.getProperty("GOOGLE_WEB_CLIENT_ID")
            ?: throw GradleException("GOOGLE_WEB_CLIENT_ID not found in local.properties")

        buildConfigField(
            "String",
            "GOOGLE_WEB_CLIENT_ID",
            "\"$googleClientId\""
        )
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

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(11)

    compilerOptions {
        // optional configs
    }
}

dependencies {
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.navigation.compose)

    // animation
    implementation("com.airbnb.android:lottie-compose:6.0.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // system ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    implementation("androidx.compose.material:material-icons-extended")

    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // permission handler
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    // location services
    implementation(libs.play.services.location)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //    hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // for google signin
    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

//    data store
    implementation("androidx.datastore:datastore-preferences:1.1.0")

    // for fake repository
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")

    // coil
    implementation("io.coil-kt:coil-compose:2.6.0")
}