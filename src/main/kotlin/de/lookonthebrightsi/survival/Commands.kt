package de.lookonthebrightsi.survival

import de.hglabor.training.utils.extensions.onlinePlayers
import de.hglabor.training.utils.sendCommand
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.*
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.kick
import net.axay.kspigot.extensions.geometry.blockLoc
import net.md_5.bungee.api.ChatColor

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

    command("survival") {
        requiresPermission("survival.survival")
        literal("spawnpos1") {
            runs {
                Config.SPAWN_POS_1.set(player.location.blockLoc)
                player.sendMessage("$PREFIX Set Spawn Pos 1 to your current location.")
            }
        }
        literal("spawnpos2") {
            runs {
                Config.SPAWN_POS_2.set(player.location.blockLoc)
                player.sendMessage("$PREFIX Set Spawn Pos 2 to your current location.")
            }
        }
        literal("spawntppos") {
            runs {
                Config.SPAWN_TELEPORTER_POS.set(player.location.blockLoc)
                player.sendMessage("$PREFIX Set Spawn Teleport Pos to your current location.")
            }
        }
        literal("reload") {
            runs {
                Config.reload()
                player.sendMessage("$PREFIX ${KColors.GREEN}Reloaded config.")
            }
        }
    }
}