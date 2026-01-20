apply(plugin = "maven-publish")
apply(plugin = "signing")

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("release") {
            // The coordinates of the library, being set from variables that
            // we'll set up in a moment
            groupId = project.property("GROUP") as String
            artifactId = project.property("POM_ARTIFACT_ID") as String
            version = (project.extra["getVersionName"] as () -> String)()
            
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
            
            // Self-explanatory metadata for the most part
            pom {
                name.set(project.property("POM_NAME") as String)
                packaging = project.property("POM_PACKAGING") as String
                description.set(project.property("POM_DESCRIPTION") as String)
                url.set(project.property("POM_URL") as String)
                
                licenses {
                    license {
                        name.set(project.property("POM_LICENCE_NAME") as String)
                        url.set(project.property("POM_LICENCE_URL") as String)
                        distribution.set(project.property("POM_LICENCE_DIST") as String)
                    }
                }
                
                developers {
                    developer {
                        id.set(project.property("POM_DEVELOPER_ID") as String)
                        name.set(project.property("POM_DEVELOPER_NAME") as String)
                    }
                }
                
                scm {
                    url.set(project.property("POM_SCM_URL") as String)
                    connection.set(project.property("POM_SCM_CONNECTION") as String)
                    developerConnection.set(project.property("POM_SCM_DEV_CONNECTION") as String)
                }
                
                // A slight fix so that the generated POM will include any transitive dependencies
                // that the library builds upon
                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    
                    fun addDependency(dep: Dependency, scope: String) {
                        if (dep.group == null || dep.version == null || dep.name == null || dep.name == "unspecified") {
                            return // invalid dependencies should be ignored
                        }
                        
                        val dependencyNode = dependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("artifactId", dep.name)
                        if (dep.version == "unspecified") {
                            dependencyNode.appendNode("groupId", project.extra["pomGroupID"])
                            dependencyNode.appendNode("version", project.extra["pomVersion"])
                        } else {
                            dependencyNode.appendNode("groupId", dep.group)
                            dependencyNode.appendNode("version", dep.version)
                        }
                        dependencyNode.appendNode("scope", scope)
                    }
                    
                    configurations["api"].dependencies.forEach { dep -> addDependency(dep, "compile") }
                    configurations["implementation"].dependencies.forEach { dep -> addDependency(dep, "runtime") }
                }
            }
        }
    }
}

configure<SigningExtension> {
    val signingKeyId = System.getenv("SIGNING_KEY_ID")
    val signingKey = System.getenv("SIGNING_PRIVATE_KEY_BASE64")
    val signingPassword = System.getenv("SIGNING_KEY_PASSWORD")
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(extensions.getByType<PublishingExtension>().publications)
}

tasks.named("publish") {
    dependsOn("build")
}

tasks.named("publishToMavenLocal") {
    dependsOn("build")
}

tasks.named("publishToSonatype") {
    dependsOn("publish")
}
