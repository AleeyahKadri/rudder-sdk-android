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
} else {
    // Use system environment variables
    extra["ossrhUsername"] = System.getenv("OSSRH_USERNAME") ?: ""
    extra["ossrhPassword"] = System.getenv("OSSRH_PASSWORD") ?: ""
    extra["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID") ?: ""
    extra["signing.keyId"] = System.getenv("SIGNING_KEY_ID") ?: ""
    extra["signing.password"] = System.getenv("SIGNING_PASSWORD") ?: ""
    extra["signing.key"] = System.getenv("SIGNING_KEY") ?: ""
}

configure<io.github.gradlenexus.publishplugin.NexusPublishExtension> {
    repositories {
        sonatype {
            username.set(extra["ossrhUsername"] as String)
            password.set(extra["ossrhPassword"] as String)
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
        }
    }
}
