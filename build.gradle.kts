import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    application

    id("com.github.ben-manes.versions") version "0.51.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
}

group = "robertw"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-csv:1.14.1")

    // slf4j comes indirectly with kotlin-logging
    implementation("io.github.microutils:kotlin-logging:4.0.0-beta-2")
    implementation("ch.qos.logback:logback-classic:1.5.23")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.20.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.27.6")
}

kotlin {
    jvmToolchain(17)
}

detekt {
    config.setFrom("detekt.yml")
    buildUponDefaultConfig = false
    autoCorrect = true
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// https://github.com/ben-manes/gradle-versions-plugin
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}