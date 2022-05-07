package de.lookonthebrightsi.survival

import org.bukkit.entity.Player
import java.util.*


data class PlayerProperties(var inSpawn: Boolean, var gliding: Boolean, var goingToSpawn: Boolean)

private val propertiesMap = HashMap<UUID, PlayerProperties>()

val Player.properties get() = propertiesMap.getOrPut(uniqueId) { PlayerProperties(inSpawnRegion(), gliding = false, goingToSpawn = false) }