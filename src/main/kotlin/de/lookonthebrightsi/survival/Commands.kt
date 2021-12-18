package de.lookonthebrightsi.survival

import de.hglabor.utils.kutils.onlinePlayers
import de.hglabor.utils.kutils.sendCommand
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.*
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.feedSaturate
import net.axay.kspigot.extensions.bukkit.heal
import net.axay.kspigot.extensions.geometry.blockLoc
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player

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
                        sender.bukkitSender.sendMessage("$PREFIX ${KColors.RED}Could not parse color ${KColors.WHITE}$color ${KColors.RED}.")
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
                    kickPlayer("${KColors.RED}Server is restarting\n\n- ${KColors.ORANGE}${getArgument<String>("message")} ${KColors.RED}-\n\n\n${KColors.YELLOW}The server should be back soon")
                }
                sendCommand("stop")
            }
        }
        runs {
            onlinePlayers {
                kickPlayer("${KColors.RED}Server is restarting\n\n\n${KColors.YELLOW}The server should be back soon.")
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
                sender.bukkitSender.sendMessage("$PREFIX ${KColors.GREEN}Reloaded config.")
            }
        }

        literal("setdeaths") {
            argument<String>("player") {
                argument<String>("amount") {
                    runs {
                        Bukkit.getPlayer(getArgument<String>("player"))?.setStatistic(Statistic.DEATHS, getArgument<String>("amount").toInt())
                            ?: sender.bukkitSender.sendMessage("$PREFIX ${KColors.RED}Could not find player with name ${KColors.WHITE}${getArgument<String>("player")}${KColors.RED}.")
                    }
                }
            }
        }
    }

    command("heal") {
        requiresPermission("survival.heal")
        runs {
            player.apply {
                heal()
                feedSaturate()
                sender.bukkitSender.sendMessage("$PREFIX ${KColors.GREEN}You got healed.")
            }
        }
        argument<String>("player") {
            runs {
                val playerName = getArgument<String>("player")
                Bukkit.getPlayer(playerName)?.apply {
                    heal()
                    feedSaturate()
                    this@runs.player.sendMessage("$PREFIX ${KColors.GREEN}Healed $name.")
                    sendMessage("$PREFIX ${KColors.GREEN}You got healed.")
                } ?: run {
                    sender.bukkitSender.sendMessage("$PREFIX ${KColors.RED}Unknown player: ${KColors.WHITE}$playerName")
                }
            }
        }
    }

    command("day") {
        requiresPermission("survival.day")
        runs {
            sendCommand("time set day")
            sender.bukkitSender.sendMessage("$PREFIX ${KColors.GREEN}Set the time to day.")
        }
    }

    command("night") {
        requiresPermission("survival.night")
        runs {
            sendCommand("time set night")
            sender.bukkitSender.sendMessage("$PREFIX ${KColors.ORANGE}Set the time to night.")
        }
    }
    command("prefix") {
        literal("remove") {
            runs {
                sendCommand("lp user ${player.name} meta removeprefix 100")
                player.sendMessage("$PREFIX ${KColors.ORANGE}Your prefix was removed.")
            }
        }
        argument<String>("prefix") {
            runs {
                player.setPrefix("${KColors.GREEN}[${getArgument<String>("prefix")}]")
            }
            argument<String>("color") {
                runs {
                    val color = getArgument<String>("color")
                    player.setPrefix("${ChatColor.of(color)}[${getArgument<String>("prefix")}]")
                }
            }
        }
    }
}

fun Player.setPrefix(prefix: String) {
    sendCommand("lp user $name meta removeprefix 100")
    sendCommand("lp user $name meta addprefix 100 \"$prefix\"")
    sendMessage("$PREFIX ${KColors.SPRINGGREEN}Your prefix was changed to $prefix")
}