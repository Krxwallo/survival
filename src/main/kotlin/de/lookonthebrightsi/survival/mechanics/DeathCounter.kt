package de.lookonthebrightsi.survival.mechanics

import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.Criterias
import org.bukkit.scoreboard.DisplaySlot

object DeathCounter {
    private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    private val deathsPlayerList = scoreboard.getObjective("deathsPlayerList") ?: scoreboard.registerNewObjective("deathsPlayerList", Criterias.DEATHS, Component.text("Tode"))
    private val deathsBelowName = scoreboard.getObjective("deathsBelowName") ?: scoreboard.registerNewObjective("deathsBelowName", Criterias.DEATHS, Component.text("Tode"))
    init {
        deathsPlayerList.displaySlot = DisplaySlot.PLAYER_LIST
        deathsBelowName.displaySlot = DisplaySlot.BELOW_NAME

        listen<PlayerJoinEvent> {
            it.player.scoreboard = scoreboard
        }
    }

    fun setDeaths(player: OfflinePlayer, amount: Int) {
        deathsPlayerList.getScore(player).score = amount
        deathsBelowName.getScore(player).score = amount
    }
}