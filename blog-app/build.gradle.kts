
plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    // module
    implementation(project(":blog-app-domain"))
    implementation(project(":blog-app-service"))
    implementation(project(":blog-app-data"))
    implementation(project(":blog-app-external-api"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${rootProject.extra.get("ktorVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-extensions-spring:${rootProject.extra.get("kotestVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}
