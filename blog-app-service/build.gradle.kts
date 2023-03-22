
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

	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.ktor:ktor-serialization-kotlinx-json:${rootProject.extra.get("ktorVersion")}")

	// test
	testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
	testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
}
