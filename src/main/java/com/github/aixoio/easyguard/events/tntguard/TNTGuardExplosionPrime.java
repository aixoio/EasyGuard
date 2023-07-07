package com.github.aixoio.easyguard.events.tntguard;

import com.github.aixoio.easyguard.EasyGuard;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class TNTGuardExplosionPrime implements Listener {

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("TNTGUARD_ON");
        boolean showParticles = EasyGuard.getPlugin().getConfig().getBoolean("TNTGuardShowParticles");
        int particleCount = EasyGuard.getPlugin().getConfig().getInt("TNTGuardParticleCount");


        if (!active) return;

        if (e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.MINECART_TNT || e.getEntityType() == EntityType.ENDER_CRYSTAL) {

            if (showParticles) {

                Location location = e.getEntity().getLocation();

                e.getEntity().getWorld().spawnParticle(Particle.SMOKE_LARGE, location, particleCount);

            }

            e.getEntity().remove();
            e.setCancelled(true);

        }

    }

}
