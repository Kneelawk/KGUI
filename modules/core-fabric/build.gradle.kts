plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
    id("com.gradleup.shadow")
}

evaluationDependsOn(":engine")

submodule {
    setLibsDirectory()
    applyXplatConnection(":core-xplat")
    setupJavadoc()
}

val shadowInclude = configurations.create("shadowInclude")

dependencies {
    compileOnly(project(":engine"))

    // we shade kdl4j as it is not an official release
    val kdl4j_version: String by project
    compileOnly("kdl:kdl4j:$kdl4j_version")
    localRuntime("kdl:kdl4j:$kdl4j_version")
    shadowInclude("kdl:kdl4j:$kdl4j_version")
}

tasks {
    // shade the engine module
    val engineSource = project(":engine").sourceSets.main

    processResources.configure {
        from(engineSource.map { it.resources })
    }
    withType<JavaCompile>().configureEach {
        source(engineSource.map { it.allJava })
    }
    sourcesJar.configure {
        from(engineSource.map { it.allSource })
    }
    javadoc.configure {
        source(engineSource.map { it.allJava })
    }

    shadowJar {
        configurations = listOf(shadowInclude)
        archiveClassifier.set("shadow-dev")
        destinationDirectory.set(project.layout.buildDirectory.dir("devlibs"))
        relocate("kdl", "com.kneelawk.kgui.engine.kdl")
        dependencies {
            exclude(dependency("jakarta.annotation:jakarta.annotation-api"))
        }
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
    }
}

kpublish {
    createPublication()
}
