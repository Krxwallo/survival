@file:Suppress("PropertyName")

group = "de.lookonthebrightsi"
version = "0.0.1"

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    }
}

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    // Spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    // FAWE
    maven("https://mvn.intellectualsites.com/content/repositories/releases/")
    // CloudNet
    maven("https://repo.cloudnetservice.eu/repository/releases/")
}

dependencies {
    implementation(kotlin("reflect"))
    // CraftBukkit
    compileOnly("org.bukkit", "craftbukkit", "1.18-rc3-R0.1-SNAPSHOT")
    // PAPER
    compileOnly("org.spigotmc:spigot-api:1.18-rc3-R0.1-SNAPSHOT")
    // FAWE
    compileOnly("com.intellectualsites.fawe:FAWE-Bukkit:1.16-637")
    // KSPIGOT
    implementation("net.axay:kspigot:1.17.4")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.0-RC")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}