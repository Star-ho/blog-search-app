plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies{
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:${rootProject.extra.get("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${rootProject.extra.get("kotestVersion")}")
    //TODO: 2023/03/20 버전 합치기  - 성호
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}
