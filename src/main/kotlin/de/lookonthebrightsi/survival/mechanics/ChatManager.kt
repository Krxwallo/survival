@file:Suppress("DEPRECATION")

package de.lookonthebrightsi.survival.mechanics

import net.axay.kspigot.event.listen
import org.bukkit.ChatColor
import org.bukkit.event.player.AsyncPlayerChatEvent

object ChatManager {
    init {
        listen<AsyncPlayerChatEvent> {
            val color = if (it.player.hasPermission("survival.vip")) ChatColor.AQUA else ChatColor.GRAY
            it.format = "$color${it.player.name}${ChatColor.GRAY}: ${ChatColor.WHITE}${it.message}"
        }
    }
}