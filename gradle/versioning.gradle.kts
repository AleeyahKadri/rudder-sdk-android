fun isReleaseBuild(): Boolean {
    return project.hasProperty("release")
}

fun getVersionName(): String {
    val versionName = project.property("VERSION_NAME") as String
    return if (isReleaseBuild()) versionName else "$versionName-SNAPSHOT"
}

extra["isReleaseBuild"] = ::isReleaseBuild
extra["getVersionName"] = ::getVersionName
