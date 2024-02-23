plugins {
    kotlin("jvm") version "1.9.22"
}

group = "world.anhgelus.kordify.customcommand"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(files("build/libs/kordify-0.4.0-all.jar"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}