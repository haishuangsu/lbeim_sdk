pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // 添加 Gradle Plugin Portal 作为备选仓库
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "lbeim_sdk"
include(":LbeIMSdk")