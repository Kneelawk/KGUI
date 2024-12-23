plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    setLibsDirectory()
    applyXplatConnection(":core-xplat")
    setupJavadoc()
}

dependencies {
    api(project(":engine"))
}

kpublish {
    createPublication()
}
