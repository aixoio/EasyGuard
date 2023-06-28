package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.Bukkit;
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


        try {

            SQLiteClaimData claimData = EasyGuard.SQLITE_MANAGER.getClaim(player.getUniqueId().toString(), location);

            if (claimData == null) {

                sender.sendMessage(ChatColor.RED + "Not found!");
                return true;

            }

            ProtectedRegion region = null;

            try {

                region = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld(claimData.getWorld()))).getRegion(claimData.getTruename());

            } catch (Exception e) {

                sender.sendMessage(ChatColor.RED + "Not found!");
                return true;

            }

            if (region == null) {

                sender.sendMessage(ChatColor.RED + "Not found!");
                return true;

            }

            sender.sendMessage(ChatColor.GREEN + "The location of " + claimData.getName() + " position 1 is:" + ChatColor.YELLOW + " X: " + region.getMinimumPoint().getX() +
                    ", Y: " + region.getMinimumPoint().getY() + ", Z: " + region.getMinimumPoint().getZ());
            sender.sendMessage(ChatColor.GREEN + "The location of " + claimData.getName() + " position 2 is:" + ChatColor.YELLOW + " X: " + region.getMaximumPoint().getX() +
                    ", Y: " + region.getMaximumPoint().getY() + ", Z: " + region.getMaximumPoint().getZ());

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "Not found!");

            EasyGuard.getPlugin().getLogger().info(e.toString());

        }

        return true;

    }

}
