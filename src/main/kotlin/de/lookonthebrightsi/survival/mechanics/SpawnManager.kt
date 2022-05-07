package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.cancel
import de.hglabor.utils.kutils.onGround
import de.hglabor.utils.kutils.stack
import de.hglabor.utils.kutils.world
import de.lookonthebrightsi.survival.Config
import de.lookonthebrightsi.survival.PREFIX
import de.lookonthebrightsi.survival.inSpawnRegion
import de.lookonthebrightsi.survival.properties
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.geometry.blockLoc
import net.axay.kspigot.items.meta
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.util.Vector

object SpawnManager {
    init {
        listen<PlayerMoveEvent> { with(it) {
            // Check if player only moved mouse
            if (to.distanceSquared(from) == 0.0) return@listen
            // Spawn Checks
            if (!player.properties.inSpawn && to.inSpawnRegion()) {
                // player entered spawn
                player.actionBar("${KColors.GREEN}Entering spawn region")
                player.properties.inSpawn = true
            }
            else if (player.properties.inSpawn && !to.inSpawnRegion()) {
                // player left spawn
                player.actionBar("${KColors.RED}Leaving spawn region")
                player.properties.inSpawn = false
                // Start gliding
                player.properties.gliding = true
                player.isGliding = true

                // Boost
                player.velocity = Vector(player.location.direction.multiply(2).x, 0.5, player.location.direction.multiply(2).z)
            }
            // Gliding checks
            // Stop gliding when on ground
            if (player.properties.gliding && player.onGround()) player.properties.gliding = false

            // Check spawn teleporter
            if (player.location.blockLoc == Config.SPAWN_TELEPORTER_POS.getLocation()) player.teleport(world("world")!!.spawnLocation)
        }}

        listen<EntityToggleGlideEvent> {
            if (it.entity is Player && (it.entity as Player).properties.gliding) it.cancel()
        }

        listen<EntityDamageEvent> {
            if (it.entity is Player && (it.entity as Player).inSpawnRegion()) it.cancel()
        }

        listen<BlockBreakEvent> {
            if (it.player.inSpawnRegion(true) && it.player.gameMode == GameMode.SURVIVAL) {
                it.cancel()
                it.player.sendMessage("$PREFIX ${KColors.RED}You can't break blocks here")
            }
        }

        listen<BlockPlaceEvent> {
            if (it.player.inSpawnRegion(true) && it.player.gameMode == GameMode.SURVIVAL) {
                it.cancel()
                it.player.sendMessage("$PREFIX ${KColors.RED}You can't place blocks here")
            }
        }

        listen<PlayerRespawnEvent> {
            // Fix respawn location
            if (it.respawnLocation.inSpawnRegion()) it.respawnLocation = it.respawnLocation.world!!.spawnLocation
        }
    }
}