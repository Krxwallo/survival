package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.cancel
import de.hglabor.utils.kutils.stack
import de.hglabor.utils.kutils.statueAttributes
import de.lookonthebrightsi.survival.Config
import net.axay.kspigot.event.listen
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.util.EulerAngle
import kotlin.math.PI

object SwordFeature {
    private val armorStand: ArmorStand
    init {
        val location = Config.SWORD_FEATURE_POS.getLocation()
        armorStand = location.world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.apply {
            statueAttributes()
            isInvisible = true
            isCustomNameVisible = false
            setArms(true)
            setBasePlate(false)
            //setRotation(0.0f, (0.5 * PI).toFloat())
            //bodyPose = EulerAngle(0.72, 1.48, 0.0)
            rightArmPose = EulerAngle(0.441 * PI, 0.5 * PI,0.0)
            this.setItem(EquipmentSlot.HAND, Material.NETHERITE_SWORD.stack().apply {
                addEnchantment(Enchantment.DAMAGE_ALL, 1)
            })
        }
        listen<PlayerInteractAtEntityEvent> {
            if (it.rightClicked == armorStand) it.cancel()
        }
    }

    fun reload() {
        armorStand.teleport(Config.SWORD_FEATURE_POS.getLocation())
    }

    fun shutdown() {
        armorStand.remove()
    }
}