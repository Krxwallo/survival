package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.stack
import de.hglabor.utils.kutils.statueAttributes
import de.lookonthebrightsi.survival.Config
import net.axay.kspigot.extensions.geometry.plus
import net.axay.kspigot.extensions.geometry.times
import net.axay.kspigot.extensions.geometry.vec
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.entity.WitherSkeleton

object WitherSkeletonFeature {
    private val skeletons = ArrayList<WitherSkeleton>()
    init {
        startup()
    }

    private fun startup() {
        val teleporterLocation = Config.SPAWN_TELEPORTER_POS.getLocation().clone().add(0.5, Config.WITHER_SKELETON_Y_OFFSET.getDouble(), 0.5)
        for (blockFace in listOf(BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST)) {
            val direction = vec(blockFace.modX, blockFace.modY, blockFace.modZ) * Config.WITHER_SKELETON_OFFSET.getInt()
            val location = teleporterLocation + direction
            val skeleton = location.world.spawnEntity(location, EntityType.WITHER_SKELETON) as WitherSkeleton
            skeletons += skeleton
            skeleton.statueAttributes()
            @Suppress("DEPRECATION")
            skeleton.customName = "${ChatColor.DARK_PURPLE}Spawn"

            skeleton.teleport(skeleton.location.apply { setDirection(direction) })
            skeleton.equipment.apply {
                helmet = Material.NETHERITE_HELMET.stack()
                chestplate = Material.NETHERITE_CHESTPLATE.stack()
                leggings = Material.NETHERITE_LEGGINGS.stack()
                boots = Material.NETHERITE_BOOTS.stack()
                setItemInMainHand(Material.NETHERITE_SWORD.stack())
                setItemInOffHand(Material.NETHERITE_SWORD.stack())
            }
        }

        /*task(period = 1) {
            skeletons.forEach {
                onlinePlayers {
                    val direction = location.toVector() - it.location.toVector()
                    val theta = atan2(-direction.x, direction.z)
                    val piTimes2 = 2 * PI
                    val yaw = Math.toDegrees((theta + piTimes2) % piTimes2).toInt().toByte()

                    val sPlayer = (this as CraftPlayer).handle
                    sPlayer.networkManager.send(ClientboundMoveEntityPacket.Rot(it.entityId, yaw, 0, true))
                }
            }
        }*/
    }

    fun reload() {
        shutdown()
        startup()
    }

    fun shutdown() {
        skeletons.forEach { it.remove() }
        skeletons.clear()
    }
}