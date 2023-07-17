package de.lookonthebrightsi.survival

import de.lookonthebrightsi.survival.mechanics.*
import net.axay.kspigot.main.KSpigot
import java.util.*

val Manager by lazy { InternalMainClass.INSTANCE }
val random = Random()

class InternalMainClass : KSpigot() {
    companion object {
        lateinit var INSTANCE: InternalMainClass; private set
    }

    override fun load() {
        INSTANCE = this
    }

    override fun startup() {
        Config.load()
        commands()
        events()

        // Mechanics
        //SpawnManager
        ChatManager
        DeathCounter
        PlayerHeads
        SpawnLocationFix
        //SwordFeature
        //ParticleFeature
        //WitherSkeletonFeature
    }

    fun reload() {
        reloadConfig()
        //SwordFeature.reload()
        //ParticleFeature.reload()
        //WitherSkeletonFeature.reload()
    }

    override fun shutdown() {
        //SwordFeature.shutdown()
        //ParticleFeature.shutdown()
        //WitherSkeletonFeature.shutdown()
    }

}