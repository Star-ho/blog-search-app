
plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

val ktorVersion="2.1.3"

dependencies {
	// module
	implementation(project(":blog-app-domain"))
	implementation(project(":blog-app-data"))
	implementation(project(":blog-app-external-api"))

	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// ktor
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-cio:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
	testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
	testImplementation("io.kotest:kotest-extensions-spring:${rootProject.extra.get("kotestVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
