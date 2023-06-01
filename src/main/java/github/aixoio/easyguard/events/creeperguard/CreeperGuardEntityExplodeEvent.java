package github.aixoio.easyguard.events.creeperguard;

import github.aixoio.easyguard.EasyGuard;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperGuardEntityExplodeEvent implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardActive");
        boolean removeCreeperEntityOnExplode = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardRemoveCreeperEntityOnExplode");
        boolean showParticles = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardShowParticles");
        int particleCount = EasyGuard.getPlugin().getConfig().getInt("CreeperGuardParticleCount");
        boolean playSound = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardPlaySound");

        if (!active) return;
        if (e.getEntityType() == null) return;
        if (e.getEntityType() != EntityType.CREEPER) return;

        e.setCancelled(true);

        if (removeCreeperEntityOnExplode) e.getEntity().remove();

        if (showParticles) {

            Location location = e.getEntity().getLocation();

            e.getEntity().getWorld().spawnParticle(Particle.SMOKE_LARGE, location, particleCount);

        }

        if (playSound) {

            Location location = e.getEntity().getLocation();

            e.getEntity().getWorld().playSound(location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);

        }

    }

}
