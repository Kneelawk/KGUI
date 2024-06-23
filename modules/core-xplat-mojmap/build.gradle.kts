plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    setLibsDirectory()
    applyFabricLoaderDependency()
    applyXplatConnection(":core-xplat", "mojmap")
    disableRemap()
    setupJavadoc()
}

dependencies {
    api(project(":engine"))
}

kpublish {
    createPublication()
}
