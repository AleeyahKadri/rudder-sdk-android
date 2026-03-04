tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(project.the<com.android.build.gradle.LibraryExtension>().sourceSets.getByName("main").java.srcDirs)
}

tasks.register<Javadoc>("javadoc") {
    configurations.getByName("implementation").isCanBeResolved = true

    isFailOnError = false
    source = project.the<com.android.build.gradle.LibraryExtension>().sourceSets.getByName("main").java.getSourceFiles()
    classpath += project.files(project.the<com.android.build.gradle.LibraryExtension>().bootClasspath.joinToString(File.pathSeparator))
    classpath += configurations.getByName("implementation")
}

// build a jar with javadoc
tasks.register<Jar>("javadocJar") {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    from(tasks.named<Javadoc>("javadoc").get().destinationDir)
}

// Attach Javadocs and Sources jar
artifacts {
    add("archives", tasks.named("sourcesJar"))
    add("archives", tasks.named("javadocJar"))
}
