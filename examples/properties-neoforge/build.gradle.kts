plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    applyNeoforgeDependency()
    applyXplatConnection(":properties-xplat", "neoforge")
    generateRuns()
}
