buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.android.library")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 19
        targetSdk = 33
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-consumer-rules.pro")
        }
    }
    namespace = "com.rudderstack.android.integration.dummy"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    compileOnly(project(":core"))
    implementation("com.google.code.gson:gson:2.8.6")

    testImplementation("com.android.support.test:rules:1.0.2")
    testImplementation("com.android.support.test:runner:1.0.2")
    testImplementation("org.robolectric:robolectric:4.3")
    testImplementation("androidx.test:core-ktx:1.2.0")
}
