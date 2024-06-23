plugins {
    `java-library`
    id("com.kneelawk.versioning")
    id("com.kneelawk.kpublish")
}

repositories {
    mavenCentral()
    maven("https://maven.kneelawk.com/releases/") { name = "Kneelawk" } // houses vendored kdl4j
}

dependencies {
    val jetbrains_annotations_version: String by project
    compileOnly("org.jetbrains:annotations:$jetbrains_annotations_version")

    val kdl4j_version: String by project
    api("kdl:kdl4j:$kdl4j_version")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.named("javadoc").configure {

}

kpublish {
    createPublication()
}
