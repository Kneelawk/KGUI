plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

evaluationDependsOn(":engine")

submodule {
    setLibsDirectory()
    applyFabricLoaderDependency()
    applyFabricApiDependency()
    applyXplatConnection(":core-xplat", "fabric")
    setupJavadoc()
}

dependencies {
    compileOnly(project(":engine"))
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
}

kpublish {
    createPublication()
}
