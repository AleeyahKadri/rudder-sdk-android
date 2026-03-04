apply(from = rootProject.file("gradle/versioning.gradle.kts"))

group = project.property("GROUP") as String
version = (extra["getVersionName"] as () -> String)()

// Create variables with empty default values
extra["signing.keyId"] = ""
extra["signing.password"] = ""
extra["signing.key"] = ""
extra["ossrhUsername"] = ""
extra["ossrhPassword"] = ""
extra["sonatypeStagingProfileId"] = ""

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    // Read local.properties file first if it exists
    val p = java.util.Properties()
    java.io.FileInputStream(secretPropsFile).use { p.load(it) }
    p.forEach { name, value -> extra[name.toString()] = value }
    println("read from local.props")
} else {
    // Use system environment variables
    extra["ossrhUsername"] = System.getenv("NEXUS_USERNAME") ?: ""
    extra["ossrhPassword"] = System.getenv("NEXUS_PASSWORD") ?: ""
    extra["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID") ?: ""
    extra["signing.password"] = System.getenv("SIGNING_PASSWORD") ?: ""
    extra["signing.key"] = System.getenv("SIGNING_PRIVATE_KEY_BASE64") ?: ""
    println("read from environment: ${extra["ossrhUsername"]}")
}

configure<io.github.gradlenexus.publishplugin.NexusPublishExtension> {
    repositories {
        sonatype {
            username.set(System.getenv("NEXUS_USERNAME"))
            password.set(System.getenv("NEXUS_PASSWORD"))

            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
        }
    }
}
