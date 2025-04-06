plugins {
    kotlin("jvm") version "2.1.10"
}

group = "com.lyni"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.tinylog:tinylog-api-kotlin:2.8.0-M1")
    implementation("org.tinylog:tinylog-impl:2.8.0-M1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}