import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinPluginVersion = "1.7.22"
    id("org.springframework.boot") version "3.0.4" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false

    kotlin("plugin.serialization") version kotlinPluginVersion apply false
    kotlin("jvm") version kotlinPluginVersion apply false
    kotlin("plugin.spring") version kotlinPluginVersion apply false
    kotlin("plugin.jpa") version kotlinPluginVersion apply false
}

group = "com.blog"
version = "0.0.1-SNAPSHOT"

subprojects{
    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}