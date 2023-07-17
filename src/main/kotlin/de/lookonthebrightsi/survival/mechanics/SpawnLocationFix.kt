package de.lookonthebrightsi.survival.mechanics

import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.geometry.add
import org.bukkit.event.player.PlayerRespawnEvent

object SpawnLocationFix {
    init {
        listen<PlayerRespawnEvent> {
            // Fix respawn location
            it.player.bedSpawnLocation ?: run { it.respawnLocation = it.respawnLocation.world!!.spawnLocation.clone().add(0.5, 0, 0.5) }
        }
    }
}