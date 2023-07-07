package com.github.aixoio.easyguard.events.locationstick;

import com.github.aixoio.easyguard.EasyGuard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;

public class LocationStickJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (!EasyGuard.getPlugin().getConfig().getBoolean("LOCATION_STICK_ACTIVE")) return;
        if (!EasyGuard.getPlugin().getConfig().getBoolean("LOCATION_STICK_GIVE_ON_FIRST_JOIN")) return;

        Material material = Material.getMaterial(EasyGuard.getPlugin().getConfig().getString("LOCATION_STICK_ITEM"));

        if (material == null || material.isAir()) return;

        if (e.getPlayer().hasPlayedBefore()) return;

        if (EasyGuard.getPlugin().getConfig().getString("LOCATION_STICK_ITEM_NAME").equalsIgnoreCase("null")) {

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            LinkedList<String> lore = new LinkedList<String>();

            lore.add("");
            lore.add(ChatColor.GREEN + "Right click to see your current location");
            lore.add(ChatColor.YELLOW + "You can also use any " + material.toString().replace('_', ' ').toLowerCase());

            meta.setLore(lore);

            item.setItemMeta(meta);

            e.getPlayer().getInventory().addItem(item);

        } else {

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(EasyGuard.parseString(EasyGuard.getPlugin().getConfig().getString("LOCATION_STICK_ITEM_NAME")));

            LinkedList<String> lore = new LinkedList<String>();

            lore.add("");
            lore.add(ChatColor.GREEN + "Right click to see your current location");
            lore.add(ChatColor.YELLOW + "You can also use any " + material.toString().replace('_', ' ').toLowerCase());

            meta.setLore(lore);

            item.setItemMeta(meta);

            e.getPlayer().getInventory().addItem(item);

        }

    }

}
