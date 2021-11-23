package de.lookonthebrightsi.survival.utils.extensions

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.math.Vector3
import org.bukkit.Location
import org.bukkit.World

fun World.we() = BukkitWorld(this)
fun Location.we(): BlockVector3 = BlockVector3.at(x, y, z)
fun Vector3.bukkit(): Location = Location(null, x, y, z)
fun BlockVector3.bukkit(): Location = Location(null, x.toDouble(), y.toDouble(), z.toDouble())