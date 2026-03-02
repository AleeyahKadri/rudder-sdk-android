plugins {
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.register<JacocoReport>("codeCoverageReport") {
    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/**/*serializer*.*",
        "**/**/*Companion*.*"
    )
    val mainSrc = mutableListOf<String>()
    val debugTrees = mutableListOf<FileTree>()
    val execData = mutableListOf<FileTree>()
    
    if (project.name == rootProject.name) {
        subprojects.forEach {
            mainSrc.add("${it.projectDir}/src/main/java")
            debugTrees.add(fileTree("${it.buildDir}/classes").exclude(fileFilter))
            debugTrees.add(fileTree("${it.buildDir}/tmp/kotlin-classes/debugUnitTest").exclude(fileFilter))
            execData.add(fileTree("${it.buildDir}/jacoco").include("*.exec"))
        }
    } else {
        mainSrc.add("${project.projectDir}/src/main/java")
        debugTrees.add(fileTree("${project.buildDir}/classes").exclude(fileFilter))
        debugTrees.add(fileTree("${project.buildDir}/tmp/kotlin-classes/debugUnitTest").exclude(fileFilter))
        execData.add(fileTree("${project.buildDir}/jacoco").include("*.exec"))
    }

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTrees))
    executionData.setFrom(execData)

    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("${buildDir}/reports/jacoco/report.xml"))
        html.required.set(true)
        csv.required.set(false)
    }
}
