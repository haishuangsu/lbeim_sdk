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
include(":lbeim_sdk", ":LbeIMSdk")
project(":LbeIMSdk").projectDir = file("/Users/haishuangsu/flutter_workspace/LbeIM/lbeim_sdk/android/LbeIMSdk")