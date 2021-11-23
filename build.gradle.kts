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
    // Paper
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    // FAWE
    maven("https://mvn.intellectualsites.com/content/repositories/releases/")
    // CloudNet
    maven("https://repo.cloudnetservice.eu/repository/releases/")
}

dependencies {
    implementation(kotlin("reflect"))
    // CraftBukkit
    compileOnly("org.bukkit", "craftbukkit", "1.17-R0.1-SNAPSHOT")
    // PAPER
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    // FAWE
    compileOnly("com.intellectualsites.fawe:FAWE-Bukkit:1.16-637")
    // KSPIGOT
    implementation("net.axay:kspigot:1.17.4")
}

tasks {
    compileJava {
        options.release.set(16)
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }
}