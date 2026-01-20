apply(plugin = "jacoco")

configure<JacocoPluginExtension> {
    toolVersion = "0.8.7"
}

tasks.register<JacocoReport>("codeCoverageReport") {
    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/**/*serializer*.*", "**/**/*Companion*.*"
    )
    val mainSrc = mutableListOf<String>()
    val debugTrees = mutableListOf<ConfigurableFileTree>()
    val execData = mutableListOf<ConfigurableFileTree>()
    
    if (project.name == rootProject.name) {
        subprojects.forEach { subproject ->
            mainSrc.add("${subproject.projectDir}/src/main/java")
            debugTrees.add(fileTree("${subproject.buildDir}/classes") { exclude(fileFilter) })
            debugTrees.add(fileTree("${subproject.buildDir}/tmp/kotlin-classes/debugUnitTest") { exclude(fileFilter) })
            execData.add(fileTree("${subproject.buildDir}/jacoco") { include("*.exec") })
        }
    } else {
        mainSrc.add("${project.projectDir}/src/main/java")
        debugTrees.add(fileTree("${project.buildDir}/classes") { exclude(fileFilter) })
        debugTrees.add(fileTree("${project.buildDir}/tmp/kotlin-classes/debugUnitTest") { exclude(fileFilter) })
        execData.add(fileTree("${project.buildDir}/jacoco") { include("*.exec") })
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
