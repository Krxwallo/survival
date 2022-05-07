package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.*
import de.lookonthebrightsi.survival.*
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.geometry.*
import net.axay.kspigot.runnables.task
import net.axay.kspigot.runnables.taskRun
import net.axay.kspigot.runnables.taskRunLater
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPig
import org.bukkit.entity.EntityType
import org.bukkit.entity.Pig
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import kotlin.math.max

object SpawnManager {
    init {
        listen<PlayerMoveEvent> {
            checkPlayer(it.player)
        }

        listen<PlayerGameModeChangeEvent> {
            taskRun { checkPlayer(it.player) }
        }

        listen<EntityToggleGlideEvent> {
            if (it.entity is Player && (it.entity as Player).properties.gliding) it.cancel()
        }

        listen<EntityDamageEvent> {
            if (it.entity is Player && (it.entity as Player).inSpawnRegion()) it.cancel()
        }

        listen<BlockBreakEvent> {
            if (it.block.location.inSpawnProt() && it.player.gameMode == GameMode.SURVIVAL) {
                it.cancel()
                it.player.sendMessage("$PREFIX ${ChatColor.RED}You can't break blocks here")
            }
        }

        listen<ExplosionPrimeEvent> {
            if (it.entity.location.inSpawnProt()) it.cancel()
        }

        listen<FoodLevelChangeEvent> {
            if (it.entity.location.inSpawnProt()) {
                it.foodLevel = max(it.foodLevel, it.entity.foodLevel)
            }
        }

        listen<BlockPlaceEvent> {
            if ((it.blockAgainst.location.inSpawnProt() || it.blockPlaced.location.inSpawnRegion(true)) && it.player.gameMode == GameMode.SURVIVAL) {
                it.cancel()
                it.player.sendMessage("$PREFIX ${ChatColor.RED}You can't place blocks here")
            }
        }

        listen<PlayerRespawnEvent> {
            // Fix respawn location
            it.player.bedSpawnLocation ?: run { it.respawnLocation = it.respawnLocation.world!!.spawnLocation.clone().add(0.5, 0, 0.5) }
        }

        listen<VehicleExitEvent> {
            if (it.exited !is Player) return@listen
            val player = it.exited as Player
            if (it.vehicle is Pig && player.properties.goingToSpawn) it.cancel()
        }

        listen<EntityDamageEvent> {
            if ((it.entity as? Player)?.properties?.goingToSpawn == true) it.cancel()
        }

        listen<PlayerQuitEvent> {
            if (it.player.properties.goingToSpawn) {
                it.player.teleport(world("world")!!.spawnLocation)
                it.player.properties.goingToSpawn = false
            }
        }
    }

    private fun checkPlayer(player: Player) {
        val location = player.location

        fun enter() {
            // player entered spawn
            player.actionBar("${ChatColor.GREEN}Entering spawn region")
            player.properties.inSpawn = true
            player.isGliding = false
            player.properties.gliding = false

            player.velocity = vec()
        }

        fun leave(glide: Boolean = true) {
            // player left spawn
            player.actionBar("${ChatColor.RED}Leaving spawn region")
            player.properties.inSpawn = false

            if (!glide) return
            // Start gliding
            player.properties.gliding = true
            player.isGliding = true

            // Boost
            val boost = location.toVector() - location.world.spawnLocation.toVector()
            boost.normalize()
            boost.y = 0.5
            player.velocity = boost
        }

        // Spawn Checks
        if (player.isCreative()) {
            if (player.properties.inSpawn) leave(false)
            if (player.properties.gliding) {
                player.isGliding = false
                player.properties.gliding = false
            }
            return
        }

        if (!player.properties.inSpawn && location.inSpawnRegion()) enter()
        else if (player.properties.inSpawn && !location.inSpawnRegion()) leave()

        // Gliding checks
        // Stop gliding when on ground
        if (player.properties.gliding && player.onGround()) player.properties.gliding = false

        // Check spawn teleporter
        if (player.location.blockLoc == Config.SPAWN_TELEPORTER_POS.getLocation() && !player.properties.goingToSpawn) {
            player.properties.goingToSpawn = true
            if (player.properties.gliding) {
                player.isGliding = false
                player.properties.gliding = false
            }
            teleportAnimation(player)
        }
    }

    private fun Pig.teleportWithPassenger(location: Location) {
        (this as CraftPig).handle.teleportTo(location.x, location.y, location.z)
    }

    private fun teleportAnimation(player: Player) {
        val location = Config.SPAWN_TELEPORTER_POS.getLocation().clone().add(0.5, 0, 0.5)
        val spawnLocation = world("world")!!.spawnLocation
        val pig = location.world.spawnEntity(location, EntityType.PIG) as Pig
        pig.statueAttributes()
        pig.setAI(true)
        pig.addPassenger(player)
        pig.isInvisible = true

        task(period = 1) {
            pig.velocity = vecY(1)
            if (pig.location.y >= spawnLocation.y) {
                it.cancel()
                val direction = (spawnLocation.toVector() - pig.location.toVector()).normalize() * 0.5
                pig.velocity = direction
                taskRunLater(10) {
                    it.cancel()
                    player.properties.goingToSpawn = false
                    pig.remove()
                }
            }
        }
    }
}