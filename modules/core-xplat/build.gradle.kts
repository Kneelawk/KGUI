plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    setLibsDirectory()
    setRefmaps("kgui-core")
    setupJavadoc()
}

dependencies {
    api(project(":engine"))
}

kpublish {
    createPublication("intermediary")
}
