@file:Suppress("PropertyName")

group = "de.lookonthebrightsi"
version = "0.0.2"
val kspigot = "1.18.0"
val kutils = "0.0.2"

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("io.papermc.paperweight.userdev") version "1.3.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

bukkit {
    main = "de.lookonthebrightsi.survival.InternalMainClass"
    website = "https://github.com/Krxwallo/survival"
    version = project.version.toString()
    apiVersion = "1.18"
    authors = listOf("Krxwallo")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-reflect:1.6.0",
        "net.axay:kspigot:$kspigot",
        "de.hglabor.utils:kutils:$kutils"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.1-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:$kspigot")
    implementation("de.hglabor.utils:kutils:$kutils")
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