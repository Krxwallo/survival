package de.lookonthebrightsi.survival

import de.hglabor.utils.kutils.world
import org.bukkit.ChatColor
import org.bukkit.Location

val PREFIX: String = "${ChatColor.DARK_GRAY}[${ChatColor.AQUA}${ChatColor.BOLD}Server${ChatColor.DARK_GRAY}]${ChatColor.WHITE}"
val DEFAULT_LOCATION = Location(world("world"), 0.0, 0.0, 0.0)


enum class Config(private val path: String, value: Any) {
    SPAWN_POS_1("spawn_pos_1", DEFAULT_LOCATION),
    SPAWN_POS_2("spawn_pos_2", DEFAULT_LOCATION),
    SPAWN_TELEPORTER_POS("spawn_teleporter_pos", DEFAULT_LOCATION),
    SPAWN_PROT_RADIUS("spawn_prot_radius", 50),
    ;

    private val configValue: Any get() = Manager.config.get(this.path) ?: this.mValue
    private var mValue: Any = value

    companion object {
        fun load() {
            values().forEach { Manager.config.addDefault(it.path, it.configValue) }
            Manager.config.options().copyDefaults(true)
            Manager.saveConfig()
        }
        fun reload() { Manager.reloadConfig() }
    }

    fun set(value: Any) {
        mValue = value
        Manager.config.set(this.path, value)
        Manager.saveConfig()
    }

    fun getInt(): Int = this.configValue as Int
    fun getBoolean(): Boolean = this.configValue as Boolean
    fun getString(): String = this.configValue as String
    @Suppress("UNCHECKED_CAST") fun getStringList(): ArrayList<String> = this.configValue as ArrayList<String>? ?: ArrayList()
    @Suppress("UNCHECKED_CAST") fun getLocation() = this.configValue as Location
}