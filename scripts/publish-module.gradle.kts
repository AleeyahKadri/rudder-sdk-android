plugins {
    `maven-publish`
    signing
}

tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(project.the<com.android.build.gradle.LibraryExtension>().sourceSets.getByName("main").java.srcDirs)
}

artifacts {
    add("archives", tasks.named("androidSourcesJar"))
}

group = project.property("GROUP") as String
version = project.property("VERSION_NAME") as String

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                // The coordinates of the library, being set from variables that
                // we'll set up later
                groupId = project.property("GROUP") as String
                artifactId = project.property("POM_ARTIFACT_ID") as String
                version = project.property("VERSION_NAME") as String

                from(components["release"])
                artifact(tasks.named("androidSourcesJar"))

                // Mostly self-explanatory metadata
                pom {
                    name.set("RudderStack Android SDK")
                    description.set("Android SDK for RudderStack. Steer your customer data.")
                    url.set("https://github.com/rudderlabs/rudder-sdk-android")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/rudderlabs/rudder-sdk-android/blob/master/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            id.set("desusai7")
                            name.set("Desu Sai Venkat")
                            email.set("venkat@rudderstack.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/rudderlabs/rudder-sdk-android.git")
                        developerConnection.set("scm:git:ssh://github.com:rudderlabs/rudder-sdk-android.git")
                        url.set("https://github.com/rudderlabs/rudder-sdk-android/tree/master")
                    }
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        rootProject.extra["signing.keyId"] as String,
        rootProject.extra["signing.key"] as String,
        rootProject.extra["signing.password"] as String
    )
    sign(publishing.publications)
}
