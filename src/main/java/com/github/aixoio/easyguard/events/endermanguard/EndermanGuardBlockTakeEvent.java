package com.github.aixoio.easyguard.events.endermanguard;

import com.github.aixoio.easyguard.EasyGuard;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EndermanGuardBlockTakeEvent implements Listener {

    @EventHandler
    public void onEndermanBlockTake(EntityChangeBlockEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("ENDERMANGUARD_ON");

        if (!active) return;

        if (e.getEntityType() != EntityType.ENDERMAN) return;

        e.setCancelled(true);

    }

}
