package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.stack
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.meta
import org.bukkit.Material
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.meta.SkullMeta

object PlayerHeads {
    init {
        listen<PlayerDeathEvent> {
            it.drops += Material.PLAYER_HEAD.stack().apply {
                meta<SkullMeta> {
                    owningPlayer = it.player
                }
            }
        }
    }
}