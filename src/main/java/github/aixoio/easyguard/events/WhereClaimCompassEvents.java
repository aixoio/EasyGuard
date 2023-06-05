package github.aixoio.easyguard.events;

import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.CompassItemData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.LinkedList;

public class WhereClaimCompassEvents implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        LinkedList<CompassItemData> compassItemDataLinkedList = EasyGuard.compassItemDataMap.get(e.getPlayer().getUniqueId());

        if (compassItemDataLinkedList == null) return;

        for (CompassItemData item : compassItemDataLinkedList) {

            try {

                if ((e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(item.getResetItem().getItemMeta().getDisplayName())) ||
                        (e.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(item.getResetItem().getItemMeta().getDisplayName()))) {

                    e.getPlayer().setCompassTarget(item.getOriginalCompassLocation());
                    e.getPlayer().getInventory().remove(item.getItem());
                    e.getPlayer().getInventory().remove(item.getResetItem());

                    e.setCancelled(true);

                }

            } catch (Exception ignored) {}

        }

    }

}
