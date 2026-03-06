import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.10"
    application

    id("com.github.ben-manes.versions") version "0.53.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
}

group = "robertw"
version = "1.0-SNAPSHOT"

application {
    // IMPORTANT for stdout.encoding: when running in Powershell, set the encoding there (Profile.ps1)
    // regarding file.encoding: apparently, OkHttp also uses this when no charset is provided in the Content-Type header.
    applicationDefaultJvmArgs = listOf("-Dstdout.encoding=UTF-8", "-Dfile.encoding=UTF-8")
    mainClass.set("ebuparser.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.sashirestela:simple-openai:3.22.2")
    // indirect dependency of simple-openai, but we'll upgrade it this way.
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
    // optional dep'cy of simple-openai that needs explicit loading (also we use it directly for the /stats API call)
    implementation("com.squareup.okhttp3:okhttp:5.3.2")

    // implementation("org.apache.commons:commons-csv:1.14.1")
    implementation("net.java.dev.jna:jna-platform:5.18.1")

    implementation(platform("software.amazon.awssdk:bom:2.42.7"))
    implementation("software.amazon.awssdk:bedrockruntime")
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:sts")

    // the ktlint task also uses this, but in app runtime, we don't use it yet
    // implementation("io.github.oshai:kotlin-logging-jvm")
    // slf4j comes indirectly with kotlin-logging
    // implementation("ch.qos.logback:logback-classic:1.5.32")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

kotlin {
    jvmToolchain(25)
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
