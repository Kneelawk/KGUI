plugins {
    id("dev.architectury.loom") apply false
    id("com.kneelawk.submodule") apply false
}

tasks.create("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
