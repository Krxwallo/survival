package de.lookonthebrightsi.survival

import org.bukkit.Location
import org.bukkit.entity.Player
import java.lang.Double.max
import kotlin.math.min

fun Player.inSpawnRegion() = location.inSpawnRegion()
fun Location.inSpawnRegion(): Boolean = inRegion(Config.SPAWN_POS_1.getLocation(), Config.SPAWN_POS_2.getLocation())

fun Location.inRegion(spawnPos1: Location, spawnPos2: Location): Boolean {
    val x1 = min(spawnPos1.x, spawnPos2.x)
    val y1 = min(spawnPos1.y, spawnPos2.y)
    val z1 = min(spawnPos1.z, spawnPos2.z)

    val x2 = max(spawnPos1.x, spawnPos2.x)
    val y2 = max(spawnPos1.y, spawnPos2.y)
    val z2 = max(spawnPos1.z, spawnPos2.z)

    return x1 < x && x < x2 && y1 < y && y < y2 && z1 < z && z < z2
}