@file:Suppress("PropertyName")

group = "de.lookonthebrightsi"
version = "0.0.1"

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("io.papermc.paperweight.userdev") version "1.3.2"
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.1-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.18.0")
    implementation("de.hglabor.utils:kutils:0.0.2")
    implementation(kotlin("reflect"))
}

tasks {
    build {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}