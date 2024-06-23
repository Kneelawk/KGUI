plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
}

submodule {
    setRefmaps("kgui-example-properties")
    applyFabricLoaderDependency()
    forceRemap()
    xplatProjectDependency(":core")
}
