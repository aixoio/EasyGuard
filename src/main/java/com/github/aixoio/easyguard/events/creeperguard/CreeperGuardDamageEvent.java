package com.github.aixoio.easyguard.events.creeperguard;

import com.github.aixoio.easyguard.EasyGuard;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreeperGuardDamageEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardActive");
        boolean disableDamage = EasyGuard.getPlugin().getConfig().getBoolean("CreeperGuardDisableDamage");

        if (!active) return;
        if (!disableDamage) return;
        if (e.getDamager() == null) return;
        if (e.getDamager().getType() != EntityType.CREEPER) return;

        e.setCancelled(true);

    }

}
