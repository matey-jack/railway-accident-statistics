import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.21"
    application

    id("io.github.ben-manes.versions") version "0.67.0"
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
}

group = "robertw"
version = "1.0-SNAPSHOT"

application {
    // IMPORTANT for stdout.encoding: when running in Powershell, set the encoding there (Profile.ps1)
    // regarding file.encoding: apparently, OkHttp also uses this when no charset is provided in the Content-Type header.
    applicationDefaultJvmArgs = listOf("-Dstdout.encoding=UTF-8", "-Dfile.encoding=UTF-8")
    mainClass.set(System.getProperty("mainClass"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runTestAws") {
    group = "application"
    description = "Runs the standalone STS profile check"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("ebuparser.TestAwsKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("com.anthropic:anthropic-java:3.12.0")
    implementation("com.openai:openai-java:4.111.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
    // for the Lemonade /stats call
    implementation("com.squareup.okhttp3:okhttp:5.3.2")

    // implementation("org.apache.commons:commons-csv:1.14.1")
    implementation("net.java.dev.jna:jna-platform:5.18.1")

    implementation(platform("software.amazon.awssdk:bom:2.59.5"))
    implementation("software.amazon.awssdk:bedrockruntime")
    implementation("software.amazon.awssdk:apache5-client")
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:sts")
    implementation("software.amazon.awssdk:sso")
    implementation("software.amazon.awssdk:ssooidc")
    implementation("software.amazon.bedrock:aws-bedrock-token-generator:1.1.0")

    // the ktlint task also uses this, but in app runtime, we don't use it yet
    // implementation("io.github.oshai:kotlin-logging-jvm")
    // slf4j comes indirectly with kotlin-logging
    // implementation("ch.qos.logback:logback-classic:1.5.32")

    testImplementation("org.junit.jupiter:junit-jupiter:5.14.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

kotlin {
    jvmToolchain(25)
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
