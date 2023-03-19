plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.serialization")
}


dependencies {
    api(project(":blog-app-domain"))
    implementation( "org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("io.ktor:ktor-serialization-kotlinx-json:${rootProject.extra.get("ktorVersion")}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")

}
