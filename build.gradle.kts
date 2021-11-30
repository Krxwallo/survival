@file:Suppress("PropertyName")

group = "de.lookonthebrightsi"
version = "0.0.1"

plugins {
    kotlin("jvm") version "1.5.10"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
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
}

tasks {
    compileJava {
        options.release.set(17)
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}