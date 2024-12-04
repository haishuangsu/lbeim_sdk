plugins {
    id("com.android.library") version "7.3.1"
    id("org.jetbrains.kotlin.android") version "1.7.10"
}

group = "com.lbeim.lbeim_sdk"
version = "1.0-SNAPSHOT"

//buildscript {
//    val kotlin_version by extra("1.7.10")
//
//    repositories {
//        google()
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.3.1")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
//    }
//}

//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}


android {
    namespace = "com.lbeim.lbeim_sdk"

    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin", "LbeIMSdk/src/main/java")
    }

    defaultConfig {
        minSdk = 19
    }

    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.mockito:mockito-core:5.0.0")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    // 引入 LbeIMSdk 库作为依赖
    implementation(project(":LbeIMSdk"))

    // implementation(files("libs/LbeIMSdk-debug.aar"))
    // implementation("com.github.haishuangsu:LbeAndroidSdk:1.0.0")
}
