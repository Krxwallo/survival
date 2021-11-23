package de.lookonthebrightsi.survival.commands

import de.hglabor.training.utils.extensions.onlinePlayers
import de.hglabor.training.utils.sendCommand
import de.lookonthebrightsi.survival.config.PREFIX
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.chat.col
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.kick
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerCommandSendEvent
import java.lang.Exception

fun commands() {
    command("broadcast") {
        requiresPermission("survival.broadcast")
        argument<String>("message") {
            runs {
                // With default color
                broadcast("$PREFIX ${KColors.GREEN}${KColors.BOLD}${getArgument<String>("message")}")
            }
            argument<String>("color") {
                runs {
                    // With specified color
                    val color: String = getArgument<String>("color").uppercase()
                    try {
                        broadcast("$PREFIX ${ChatColor.of(getArgument<String>("color"))}${KColors.BOLD}${getArgument<String>("message")}" )
                    }
                    catch (e: IllegalArgumentException) {
                        player.sendMessage("$PREFIX ${KColors.RED}Could not parse color ${KColors.WHITE}$color ${KColors.RED}.")
                    }
                }
            }
        }
    }

    command("rs") {
        requiresPermission("survival.restart")
        argument<String>("message") {
            // restart the server and kick the players before that with the given message
            runs {
                onlinePlayers {
                    kick("${KColors.RED}Server is restarting: ${getArgument<String>("message")}")
                }
                sendCommand("stop")
            }
        }
        runs {
            onlinePlayers {
                kick("${KColors.RED}Server is restarting")
            }
            sendCommand("stop")
        }
    }
}