package com.github.aixoio.easyguard.commands;

import com.github.aixoio.easyguard.EasyGuard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.github.aixoio.easyguard.util.sqlite.SQLiteDataMode;
import com.github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class ClaimCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.claim")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        if (args.length == 0) return false;

        if (args.length < 7) return false;

        try {

            LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

            double x1 = Double.parseDouble(args[1]);
            double y1 = Double.parseDouble(args[2]);
            double z1 = Double.parseDouble(args[3]);

            double x2 = Double.parseDouble(args[4]);
            double y2 = Double.parseDouble(args[5]);
            double z2 = Double.parseDouble(args[6]);


            BlockVector3 blockVector1 = BlockVector3.at(x1, y1, z1);
            BlockVector3 blockVector2 = BlockVector3.at(x2, y2, z2);

            ApplicableRegionSet applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(localPlayer.getWorld())
                    .getApplicableRegions(
                            blockVector1
                    );

            int size = applicableRegionSet.getRegions().size();

            applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(localPlayer.getWorld())
                    .getApplicableRegions(
                            blockVector2
                    );

            size += applicableRegionSet.getRegions().size();

            boolean insideAClaim = (size != 0);

            if (insideAClaim) {

                sender.sendMessage(ChatColor.RED + "You cannot claim land that has been claimed");
                return true;

            } else {

                Random random = new Random();

                String regionName = args[0];

                String name = String.format("%s_%s_%d", player.getDisplayName().toLowerCase(), regionName, random.nextInt());

                boolean bypassSize = false;

                if (args.length == 8) {

                    String bypassstring = args[7];

                    if (bypassstring.equalsIgnoreCase("bypass") && player.hasPermission("easyguard.size-bypass")
                            && EasyGuard.getPlugin().getConfig().getBoolean("ALLOW_MAX_SIZE_BYPASS")) {

                        bypassSize = true;
                        sender.sendMessage(ChatColor.GOLD + "Bypassing size limit...");

                    } else if (!bypassstring.equalsIgnoreCase("bypass")) {

                        sender.sendMessage(ChatColor.RED + "Unknown options!");
                        return true;

                    } else {

                        sender.sendMessage(ChatColor.RED + "You cannot bypass this limit!");
                        return true;

                    }

                }

                final int MAX_SIZE = Math.abs(EasyGuard.getPlugin().getConfig().getInt("MAX_SIZE_LENGTH")) *
                        Math.abs(EasyGuard.getPlugin().getConfig().getInt("MAX_SIZE_WIDTH")) *
                        Math.abs(EasyGuard.getPlugin().getConfig().getInt("MAX_SIZE_HEIGHT"));


                if (MAX_SIZE != 0 && !bypassSize) {

                    double length = Math.abs(blockVector2.getX() - blockVector1.getX());
                    double width = Math.abs(blockVector2.getY() - blockVector1.getY());
                    double height = Math.abs(blockVector2.getZ() - blockVector1.getZ());

                    double volume = length * width * height;

                    if (MAX_SIZE < volume) {

                        if (EasyGuard.getPlugin().getConfig().getBoolean("ALLOW_MAX_SIZE_BYPASS") && player.hasPermission("easyguard.size-bypass")) {

                            sender.sendMessage(ChatColor.RED + "You cannot make a claim over " + MAX_SIZE + " blocks and your claim's size is " + volume + " blocks!");
                            sender.sendMessage(ChatColor.GREEN + "Run this command again but with bypass at the end:");
                            sender.sendMessage(ChatColor.RESET + " - " + ChatColor.GOLD + command.getUsage().replaceFirst("<command>", command.getName()) + " bypass");

                        } else {

                            sender.sendMessage(ChatColor.RED + "You cannot make a claim over " + MAX_SIZE + " blocks and your claim's size is " + volume + " blocks!");

                        }

                        return true;

                    }

                }


                if (EasyGuard.SQLITE_MANAGER.claimExists(player.getUniqueId().toString(), regionName)) {

                    sender.sendMessage(ChatColor.RED + "A claim with that name already exists!");
                    return true;

                }

                ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(name, blockVector1, blockVector2);

                DefaultDomain defaultDomain = new DefaultDomain();

                defaultDomain.addPlayer(localPlayer);

                protectedCuboidRegion.setOwners(defaultDomain);
                protectedCuboidRegion.setMembers(defaultDomain);

                WorldGuard
                        .getInstance()
                        .getPlatform()
                        .getRegionContainer().
                        get(localPlayer.getWorld()).
                        addRegion(protectedCuboidRegion);


                SQLiteClaimData claimData = new SQLiteClaimData(player.getUniqueId().toString(), regionName, (int) x1,
                        (int) y1, (int) z1, player.getWorld().getName(), name);

                EasyGuard.SQLITE_MANAGER.setClaim(claimData, SQLiteDataMode.INSERT);

                sender.sendMessage(ChatColor.GREEN + "Claim created with the name of " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + args[0]);

            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.DARK_RED + "Unknown error!");
            Bukkit.getServer().getLogger().info(e.getMessage());

        }

        return true;
    }
}
