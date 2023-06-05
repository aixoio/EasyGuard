package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            for (ProtectedRegion region : applicableRegionSet) {

                sender.sendMessage(ChatColor.GREEN + "The location of " + region.getId() + " is X: " + region.getMaximumPoint().getX() +
                        ", Y: " + region.getMaximumPoint().getY() + ", Z: " + region.getMaximumPoint().getZ());

            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "Not found!");

            EasyGuard.getPlugin().getLogger().info(e.toString());

        }

        return true;

    }

}
