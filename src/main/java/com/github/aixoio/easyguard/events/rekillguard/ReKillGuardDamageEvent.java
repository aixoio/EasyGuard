package com.github.aixoio.easyguard.events.rekillguard;

import com.github.aixoio.easyguard.EasyGuard;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReKillGuardDamageEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("KILLREGUARD_Active");

        if (!active) return;

        if (!(e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER)) return;

        Player victom = (Player) e.getEntity();
        Player attacker = (Player) e.getDamager();

        boolean allnullvictom = true;

        for (int i = 0; i < victom.getInventory().getContents().length; i++) {

            if (victom.getInventory().getContents()[i] != null) {

                allnullvictom = false;
                break;

            }

        }

        boolean allnullattacker = true;

        for (int i = 0; i < attacker.getInventory().getContents().length; i++) {

            if (attacker.getInventory().getContents()[i] != null) {

                allnullattacker = false;
                break;

            }

        }

        if (allnullattacker || allnullvictom) e.setCancelled(true);

    }

}
