package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.onlinePlayers
import net.axay.kspigot.event.listen
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.Objective

object DeathCounter {
    init {
        listen<PlayerJoinEvent> {
            it.player.updateDeathScoreboard()
        }
        listen<PlayerDeathEvent> {
            if (it.isCancelled) return@listen
            updateDeathScoreboards()
        }
    }

    private fun updateDeathScoreboards() {
        onlinePlayers { updateDeathScoreboard() }
    }

    private fun Player.updateDeathScoreboard() {
        scoreboard = scoreboard.apply {
            var mObjective: Objective? = null
            objectives.forEach {
                if (it.name == "deaths") mObjective = it
            }
            if (mObjective == null) mObjective = registerNewObjective("deaths", "none")
            mObjective!!.apply {
                Bukkit.getOfflinePlayers().forEach { p ->
                    getScore(p).score = p.getStatistic(Statistic.DEATHS)
                }
            }
        }
    }
}