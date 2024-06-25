plugins {
    `java-library`
    id("com.kneelawk.versioning")
    id("com.kneelawk.kpublish")
}

base.libsDirectory.set(project.rootProject.layout.buildDirectory.dir("libs"))

repositories {
    mavenCentral()
    maven("https://maven.kneelawk.com/releases/") { name = "Kneelawk" } // houses vendored kdl4j
}

dependencies {
    val jetbrains_annotations_version: String by project
    compileOnly("org.jetbrains:annotations:$jetbrains_annotations_version")
    
    val jsr305_version: String by project
    compileOnly("com.google.code.findbugs:jsr305:$jsr305_version")

    val kdl4j_version: String by project
    api("kdl:kdl4j:$kdl4j_version")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.named("javadoc", Javadoc::class).configure {
    (options as? StandardJavadocDocletOptions)?.apply {
        addBooleanOption("Werror", true)
    }

    val javadoc_package_name: String by project
    exclude("$javadoc_package_name/impl")
    exclude("$javadoc_package_name/**/impl")
}

kpublish {
    createPublication()
}
