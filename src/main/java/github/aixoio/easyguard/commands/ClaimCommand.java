package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import github.aixoio.easyguard.EasyGuard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

                ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(name, blockVector1, blockVector2);

                DefaultDomain defaultDomain = new DefaultDomain();

                defaultDomain.addPlayer(localPlayer);

                protectedCuboidRegion.setOwners(defaultDomain);

                WorldGuard
                        .getInstance()
                        .getPlatform()
                        .getRegionContainer().
                        get(localPlayer.getWorld()).
                        addRegion(protectedCuboidRegion);

                sender.sendMessage(ChatColor.GREEN + "Claim created with the name of " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + args[0]);

                EasyGuard.getPlugin().getConfig().set(String.format("data.%s.%s.location", player.getDisplayName(), args[0]), new Location(player.getWorld(), x1, y1, z1));
                EasyGuard.getPlugin().getConfig().set(String.format("data.%s.%s.truename", player.getDisplayName(), args[0]), name);
                EasyGuard.getPlugin().saveConfig();


            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.DARK_RED + "Unknown error!");
            Bukkit.getServer().getLogger().info(e.getMessage());

        }

        return true;
    }
}
