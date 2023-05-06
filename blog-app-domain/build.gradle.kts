plugins {
    kotlin("jvm")
}

dependencies{
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${rootProject.extra.get("ktorVersion")}")

    testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
}
