package de.lookonthebrightsi.survival

import de.hglabor.training.utils.extensions.cancel
import de.hglabor.training.utils.extensions.onGround
import de.lookonthebrightsi.survival.utils.extensions.stack
import de.lookonthebrightsi.survival.utils.extensions.world
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.bukkit.give
import net.axay.kspigot.extensions.geometry.blockLoc
import net.axay.kspigot.items.meta
import org.apache.logging.log4j.core.layout.PatternLayout
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.util.Vector

fun events() {
    listen<PlayerMoveEvent> { with(it) {
        to ?: return@listen
        // Check if player only moved mouse
        if (to!!.distanceSquared(from) == 0.0) return@listen
        // Spawn Checks
        if (!player.properties.inSpawn && to!!.inSpawnRegion()) {
            // player entered spawn
            player.actionBar("${KColors.GREEN}Entering spawn region")
            player.properties.inSpawn = true
        }
        else if (player.properties.inSpawn && !to!!.inSpawnRegion()) {
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

    listen<EntityDamageByEntityEvent> {
        if (it.damager is Player && it.entity is Player && (it.entity as Player).health - it.damage <= 0) {
            // Player was killed by another player
            // Drop head
            (it.damager as Player).give(Material.PLAYER_HEAD.stack().apply {
                meta<SkullMeta> {
                    owningPlayer = it.entity as Player
                }
            })
            it.damager.sendMessage("$PREFIX ${KColors.ORANGERED}You received the player head of ${KColors.WHITE}${it.entity.name}${KColors.ORANGERED}.")
        }
    }
}
