// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        @Suppress("DEPRECATION")
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        @Suppress("DEPRECATION")
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
