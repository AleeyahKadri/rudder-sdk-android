fun isReleaseBuild(): Boolean {
    return project.hasProperty("release")
}

fun getVersionName(): String { // If not release build add SNAPSHOT suffix
    val versionName: String by project
    return if (isReleaseBuild()) versionName else "$versionName-SNAPSHOT"
}

extra["isReleaseBuild"] = ::isReleaseBuild
extra["getVersionName"] = ::getVersionName
