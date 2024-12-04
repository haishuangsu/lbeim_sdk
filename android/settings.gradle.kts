pluginManagement {
    repositories {
        google()  // 确保配置了 Google 的插件仓库
        mavenCentral()  // 配置 Maven Central 仓库
    }
}

rootProject.name = "lbeim_sdk"
include(":LbeIMSdk")