plugins {
    `java-library`
    id("com.kneelawk.versioning")
    id("com.kneelawk.kpublish")
    id("com.gradleup.shadow")
}

val maven_group: String by project
group = maven_group

base.libsDirectory.set(project.rootProject.layout.buildDirectory.dir("libs"))

repositories {
    mavenCentral()
    maven("https://maven.kneelawk.com/releases/") { name = "Kneelawk" } // houses vendored kdl4j
}

val shadowInclude = configurations.create("shadowInclude")
val localRuntime = configurations.create("localRuntime")
configurations.runtimeClasspath.configure { extendsFrom(localRuntime) }

dependencies {
    val jetbrains_annotations_version: String by project
    compileOnly("org.jetbrains:annotations:$jetbrains_annotations_version")

    val jsr305_version: String by project
    compileOnly("com.google.code.findbugs:jsr305:$jsr305_version")

    // we shade kdl4j as it is not an official release
    val kdl4j_version: String by project
    compileOnly("kdl:kdl4j:$kdl4j_version")
    localRuntime("kdl:kdl4j:$kdl4j_version")
    shadowInclude("kdl:kdl4j:$kdl4j_version")

    val asm_version: String by project
    implementation("org.ow2.asm:asm:$asm_version")

    val slf4j_version: String by project
    implementation("org.slf4j:slf4j-api:2.0.9")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    named("javadoc", Javadoc::class).configure {
        (options as? StandardJavadocDocletOptions)?.apply {
            addBooleanOption("Werror", true)
        }

        val javadoc_package_name: String by project
        exclude("$javadoc_package_name/impl")
        exclude("$javadoc_package_name/**/impl")
    }

    jar {
        archiveClassifier.set("lite")
    }

    shadowJar {
        configurations = listOf(shadowInclude)
        archiveClassifier.set("")
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
        relocate("kdl", "com.kneelawk.kgui.engine.kdl")
        dependencies {
            exclude(dependency("jakarta.annotation:jakarta.annotation-api"))
        }
    }

    assemble { dependsOn(shadowJar) }
}

kpublish {
    createPublication()
}
