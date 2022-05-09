package de.lookonthebrightsi.survival.mechanics

import de.hglabor.utils.kutils.addY
import de.lookonthebrightsi.survival.Config
import de.lookonthebrightsi.survival.random
import net.axay.kspigot.extensions.geometry.times
import net.axay.kspigot.extensions.geometry.vecXZ
import net.axay.kspigot.particles.particle
import net.axay.kspigot.runnables.KSpigotRunnable
import net.axay.kspigot.runnables.task
import org.bukkit.Particle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object ParticleFeature {
    private var task: KSpigotRunnable? = null

    init {
        startup()
    }

    private fun startup() {
        val location = Config.SPAWN_TELEPORTER_POS.getLocation().clone().add(0.5, 0.0, 0.5)
        task = task(period = Config.TELEPORTER_PARTICLES_INTERVAL.getInt().toLong()) {
            Config.TELEPORTER_PARTICLE_TYPES.getStringList().forEach { particle ->
                val angle = random.nextDouble(2 * PI)
                val direction = (vecXZ(sin(angle), cos(angle))).normalize() * random.nextDouble(1.0, 2.0)
                val particleLocation = location.clone().add(direction).addY((0..4).random())
                particleLocation.particle(Particle.valueOf(particle.uppercase())) {
                    amount = Config.TELEPORTER_PARTICLES.getInt()
                    extra = Config.TELEPORTER_PARTICLES_SPEED.getInt() / 10
                }
            }
        }
    }

    fun reload() {
        shutdown()
        startup()
    }

    fun shutdown() {
        task?.cancel()
    }
}