package de.lookonthebrightsi.survival

import de.lookonthebrightsi.survival.mechanics.DeathCounter
import de.lookonthebrightsi.survival.mechanics.PlayerHeads
import de.lookonthebrightsi.survival.mechanics.SpawnManager
import net.axay.kspigot.main.KSpigot

val Manager by lazy { InternalMainClass.INSTANCE }

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
        DeathCounter
        PlayerHeads
        SpawnManager
    }

}