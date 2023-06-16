package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.sqlite.SQLiteDataMode;
import github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ResizeClaimCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.resize-claim")) {

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

            String regionName = args[0];

            SQLiteClaimData claimData = EasyGuard.SQLITE_MANAGER.getClaim(player.getUniqueId().toString(), regionName);

            if (claimData == null) {

                sender.sendMessage(ChatColor.RED + "Not found!");
                return true;

            }

            ProtectedRegion region = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(localPlayer.getWorld())
                    .getRegion(claimData.getTruename());

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

            if (!region.getOwners().contains(localPlayer)) return true;

            Map<Flag<?>, Object> flags = region.getFlags();

            DefaultDomain owners = region.getOwners();
            DefaultDomain members = region.getMembers();

            WorldGuard.getInstance().getPlatform().getRegionContainer().get(localPlayer.getWorld()).removeRegion(region.getId());

            ProtectedCuboidRegion newrg = new ProtectedCuboidRegion(region.getId(), blockVector1, blockVector2);

            newrg.setOwners(owners);
            newrg.setMembers(members);

            newrg.setFlags(flags);

            WorldGuard.getInstance().getPlatform().getRegionContainer().get(localPlayer.getWorld()).addRegion(newrg);

            claimData.setWorld(player.getWorld().getName());
            claimData.setX((int) x1);
            claimData.setY((int) y1);
            claimData.setZ((int) z1);

            EasyGuard.SQLITE_MANAGER.setClaim(claimData, SQLiteDataMode.UPDATE);

            sender.sendMessage(ChatColor.GREEN + "Claim resized with the name of " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + args[0]);


        } catch (Exception e) {

            sender.sendMessage(ChatColor.DARK_RED + "Unknown error!");
            Bukkit.getServer().getLogger().info(e.getMessage());

        }

        return true;
    }
}
