package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.CompassItemData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;

public class WhereClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("easyguard.where-claim")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        if (args.length < 1) return false;

        String location = args[0];

        Player player = (Player) sender;

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        try {

            Location targetLocaiton = EasyGuard.getPlugin().getConfig().getLocation(String.format("data.%s.%s.location", player.getDisplayName(), location));

            if (targetLocaiton == null) {

                sender.sendMessage(ChatColor.RED + "Not found!");
                return true;

            }


            BlockVector3 targetLocaitonAsVector = BlockVector3.at(targetLocaiton.getX(), targetLocaiton.getY(), targetLocaiton.getZ());

            ApplicableRegionSet applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(localPlayer.getWorld())
                    .getApplicableRegions(
                            targetLocaitonAsVector
                    );

            boolean giveCompass = EasyGuard.getPlugin().getConfig().getBoolean("WHERE_GIVE_COMPASS");

            for (ProtectedRegion region : applicableRegionSet) {

                sender.sendMessage(ChatColor.GREEN + "The location of " + region.getId() + " is X: " + region.getMaximumPoint().getX() +
                        ", Y: " + region.getMaximumPoint().getY() + ", Z: " + region.getMaximumPoint().getZ());

                if (giveCompass) {

                    ItemStack compass = new ItemStack(Material.COMPASS);
                    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

                    Location ogLocat = player.getCompassTarget();
                    player.setCompassTarget(new Location(player.getWorld(), region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()));

                    compassMeta.setDisplayName(String.format("%sPointing to%s %s", ChatColor.GREEN, ChatColor.BOLD, region.getId()));

                    LinkedList<String> lore = new LinkedList<String>();

                    lore.add("");
                    lore.add(String.format("%s%s%s:", ChatColor.LIGHT_PURPLE, region.getId(), ChatColor.GOLD));

                    compassMeta.setLore(lore);

                    compass.setItemMeta(compassMeta);

                    ItemStack resetItem = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta resetItemMeta = resetItem.getItemMeta();

                    resetItemMeta.setDisplayName(ChatColor.RED + "Right click to remove your compass");

                    resetItemMeta.addEnchant(Enchantment.LUCK, 1, true);
                    resetItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                    resetItem.setItemMeta(resetItemMeta);

                    EasyGuard.compassItemDataMap.putIfAbsent(player.getUniqueId(), new LinkedList<CompassItemData>());

                    LinkedList<CompassItemData> compassItemDataLinkedList = EasyGuard.compassItemDataMap.get(player.getUniqueId());

                    compassItemDataLinkedList.add(new CompassItemData(compass, resetItem, ogLocat));

                    EasyGuard.compassItemDataMap.put(player.getUniqueId(), compassItemDataLinkedList);

                    player.getInventory().addItem(compass);
                    player.getInventory().addItem(resetItem);

                }

            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "Not found!");

            EasyGuard.getPlugin().getLogger().info(e.toString());

        }

        return true;

    }

}
