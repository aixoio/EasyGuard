package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
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

public class DeleteClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.delete-claim")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        if (args.length == 0) return false;


        String location = args[0];

        try {

            SQLiteClaimData claimData = EasyGuard.SQLITE_MANAGER.getClaim(player.getUniqueId().toString(), location);

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

            DefaultDomain owners = region.getOwners();

            if (!owners.contains(localPlayer)) {

                sender.sendMessage(ChatColor.RED + "You cannot do that!");
                return true;

            }

            WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(localPlayer.getWorld())
                    .removeRegion(region.getId());

            EasyGuard.SQLITE_MANAGER.deleteClaim(claimData.getId());

            sender.sendMessage(ChatColor.GREEN + region.getId() + " was deleted!");

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "Not found!");

            EasyGuard.getPlugin().getLogger().info(e.toString());

        }

        return true;

    }

}
