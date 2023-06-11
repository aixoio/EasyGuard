package github.aixoio.easyguard.events.locationstick;

import github.aixoio.easyguard.EasyGuard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LocationStickInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (!EasyGuard.getPlugin().getConfig().getBoolean("LOCATION_STICK_ACTIVE")) return;

        Material material = Material.getMaterial(EasyGuard.getPlugin().getConfig().getString("LOCATION_STICK_ITEM"));

        if (material == null || material.isAir()) return;

        Player player = e.getPlayer();

        boolean iteminmainhand = (player.getInventory().getItemInMainHand().getType() == material);
        boolean iteminmainoffhand = (player.getInventory().getItemInOffHand().getType() == material);

        if (!iteminmainhand && !iteminmainoffhand) return;

        if (e.getClickedBlock() == null) {

            String cmsg = String.format("%sYour current position is: %sX: %s, Y: %s, Z: %s", ChatColor.GREEN, ChatColor.YELLOW,
                    Math.round(player.getLocation().getX()), Math.round(player.getLocation().getY()), Math.round(player.getLocation().getZ()));

            if (EasyGuard.lastLocationStickMsg.get(player.getUniqueId()) == null) player.sendMessage(cmsg);

            if (EasyGuard.lastLocationStickMsg.get(player.getUniqueId()) != null &&
                    !EasyGuard.lastLocationStickMsg.get(player.getUniqueId()).equalsIgnoreCase(cmsg)) player.sendMessage(cmsg);

            EasyGuard.lastLocationStickMsg.put(player.getUniqueId(), cmsg);

        } else {

            String cmsg = String.format("%sYour current position is: %sX: %s, Y: %s, Z: %s", ChatColor.GREEN, ChatColor.YELLOW,
                    Math.round(e.getClickedBlock().getLocation().getX()), Math.round(e.getClickedBlock().getLocation().getY()), Math.round(e.getClickedBlock().getLocation().getZ()));

            if (EasyGuard.lastLocationStickMsg.get(player.getUniqueId()) == null) player.sendMessage(cmsg);

            if (EasyGuard.lastLocationStickMsg.get(player.getUniqueId()) != null &&
                    !EasyGuard.lastLocationStickMsg.get(player.getUniqueId()).equalsIgnoreCase(cmsg)) player.sendMessage(cmsg);

            EasyGuard.lastLocationStickMsg.put(player.getUniqueId(), cmsg);

        }

        e.setCancelled(true);

    }

}
