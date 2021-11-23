package de.lookonthebrightsi.survival.main

import de.lookonthebrightsi.survival.commands.commands
import de.lookonthebrightsi.survival.config.Config
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
    }

}


