@file:Suppress("GradlePackageUpdate")

group = "de.lookonthebrightsi"
version = "1.17-0.0.2"
val kspigot = "1.17.4"
val kutils = "0.0.19"

plugins {
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

bukkit {
    main = "de.lookonthebrightsi.survival.InternalMainClass"
    website = "https://github.com/Krxwallo/survival"
    version = project.version.toString()
    apiVersion = "1.17"
    authors = listOf("Krxwallo")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.bukkit", "craftbukkit", "1.17-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:$kspigot")
    implementation("de.hglabor.utils:kutils:$kutils")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

fun com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.simpleRelocate(pattern: String) {
    relocate(pattern, "${project.group}.${project.name.toLowerCase()}.shadow.$pattern")
}