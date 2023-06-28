package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.sqlite.SQLiteManager;
import github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class CurrentClaimCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.current-claim")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        BlockVector3 location = BlockVector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        ApplicableRegionSet applicableRegionSet = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(localPlayer.getWorld())
                .getApplicableRegions(
                        location
                );

        for (ProtectedRegion region : applicableRegionSet) {

            String regionID = region.getId();

            try {
                SQLiteClaimData claimData = EasyGuard.SQLITE_MANAGER.getClaimByRegionID(regionID);
                sender.sendMessage(ChatColor.GOLD + "Looking up claim...");
                sender.sendMessage(String.format("%sYou are currently in %s%s%s%s claimed by %s%s%s", ChatColor.BLUE, ChatColor.GOLD, ChatColor.BOLD, claimData.getName(),
                        ChatColor.BLUE, ChatColor.GOLD, ChatColor.BOLD, Bukkit.getOfflinePlayer(UUID.fromString(claimData.getUuid())).getName()));

            } catch (SQLException e) {
                sender.sendMessage(String.format("%sYou are currently in %s%s%s", ChatColor.BLUE, ChatColor.GOLD, ChatColor.BOLD, region.getId()));
                throw new RuntimeException(e);
            }

        }

        if (applicableRegionSet.size() == 0) {

            sender.sendMessage(ChatColor.RED + "You are not inside a claim right now!");

        }

        return true;
    }
}
