import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

//id("com.google.gms.google-services")
val properties = Properties()
val localPropertiesFile = project.rootProject.file("sample-kotlin/local.properties")
if (localPropertiesFile.canRead()) {
    localPropertiesFile.inputStream().use { properties.load(it) }
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.testapp1mg"
        minSdk = 21
        targetSdk = 32
        multiDexEnabled = true
        versionCode = 4
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "DATA_PLANE_URL", properties.getProperty("dataplaneUrl", "\"https://api.rudderstack.com\""))
        buildConfigField("String", "CONTROL_PLANE_URL", properties.getProperty("controlplaneUrl", "\"https://api.rudderstack.com\""))
        buildConfigField("String", "WRITE_KEY", properties.getProperty("writeKey", "\"\""))
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.rudderstack.android.sample.kotlin"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.1.0")

    // Rudder Android Core SDK
    implementation(project(":core"))
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("androidx.work:work-runtime:2.7.1")

    //sql-cipher
    implementation("net.zetetic:sqlcipher-android:4.5.6@aar")
    implementation("androidx.sqlite:sqlite:2.3.1")

    // required for new life cycle methods
    implementation("androidx.lifecycle:lifecycle-process:2.6.1")
    implementation("androidx.lifecycle:lifecycle-common:2.6.1")

    // SSL Back-porting
    implementation("com.google.android.gms:play-services-base:17.6.0")

    // Amplitude Device Mode
    implementation("com.rudderstack.android.integration:amplitude:1.+")
    implementation("com.amplitude:android-sdk:2.25.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("com.google.android.gms:play-services-ads:22.1.0")

    // Braze Device Mode
    implementation("com.rudderstack.android.integration:braze:1.0.6")
    implementation("com.appboy:android-sdk-ui:21.0.0")

    //work-manager
    implementation("androidx.work:work-runtime:2.8.1")

    // required for auto collection of advertisingId
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
