plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
}

submodule {
    setRefmaps("kgui-example-properties")
    xplatProjectDependency(":core")
}
