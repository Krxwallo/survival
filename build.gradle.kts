group = "de.lookonthebrightsi"
version = "1.18.2-1.0.0"
val kspigot = "1.18.2"
val kutils = "0.0.20"

plugins {
    kotlin("jvm") version "1.6.0"
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
        "net.axay:kspigot:$kspigot",
        "de.hglabor.utils:kutils:$kutils"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
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