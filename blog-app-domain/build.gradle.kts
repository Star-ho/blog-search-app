plugins {
    kotlin("jvm")
}

dependencies{
    testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
}
