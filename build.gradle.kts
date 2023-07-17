group = "de.lookonthebrightsi"
version = "1.20.0"
val kspigot = "1.20.1"
val kutils = "1.0.0-beta"

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.papermc.paperweight.userdev") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

bukkit {
    main = "de.lookonthebrightsi.survival.InternalMainClass"
    website = "https://github.com/Krxwallo/survival"
    version = project.version.toString()
    apiVersion = "1.20"
    authors = listOf("Krxwallo")
    libraries = listOf(
        "net.axay:kspigot:$kspigot",
        "de.hglabor.utils:kutils:$kutils"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:$kspigot")
    implementation("de.hglabor.utils:kutils:$kutils")
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