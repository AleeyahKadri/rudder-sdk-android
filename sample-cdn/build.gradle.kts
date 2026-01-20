buildscript {
    extra["kotlin_version"] = "1.8.10"
    repositories {
        google()
        @Suppress("DEPRECATION")
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlin_version"]}")
    }
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
}

val kotlinVersion = extra["kotlin_version"] as String

android {
    compileSdk = 33
    
    defaultConfig {
        applicationId = "com.example.testapp1mg"
        minSdk = 21
        targetSdk = 33
        versionCode = 4
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    namespace = "com.rudderstack.android.sample.kotlin"
}

repositories {
    @Suppress("DEPRECATION")
    maven { url = uri("https://dl.bintray.com/rudderstack/rudderstack") }
    maven { url = uri("https://appboy.github.io/appboy-android-sdk/sdk") }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("com.google.android.material:material:1.2.1")
    
    implementation("com.rudderstack.android.sdk:core:[1.0.0, 2.0.0)")
    implementation("com.google.code.gson:gson:2.8.6")
    
    implementation("com.google.android.gms:play-services-ads:22.1.0")
    
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
