package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
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

                DefaultDomain owners = region.getOwners();

                if (!owners.contains(localPlayer)) {

                    sender.sendMessage(ChatColor.RED + "You cannot do that!");
                    continue;

                }

                WorldGuard
                        .getInstance()
                        .getPlatform()
                        .getRegionContainer()
                        .get(localPlayer.getWorld())
                        .removeRegion(region.getId());

                EasyGuard.getPlugin().getConfig().set(String.format("data.%s.%s", player.getDisplayName(), location), null);
                EasyGuard.getPlugin().saveConfig();

                sender.sendMessage(ChatColor.GREEN + region.getId() + " was deleted!");

            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "Not found!");

            EasyGuard.getPlugin().getLogger().info(e.toString());

        }

        return true;

    }

}
