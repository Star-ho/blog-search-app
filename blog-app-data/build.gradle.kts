plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
}


dependencies {
    api(project(":blog-app-domain"))

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg{
    annotation("javax.persistence.Entity")
}