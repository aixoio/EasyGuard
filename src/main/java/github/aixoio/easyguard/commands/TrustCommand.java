package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class TrustCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.trust")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        if (args.length == 0) {

            return false;

        }


        String mode = args[0];

        if (mode.toLowerCase().equals("add") && args.length < 4) return false;
        if (mode.toLowerCase().equals("remove") && args.length < 3) return false;
        if (mode.toLowerCase().equals("list") && args.length < 2) return false;
        if (mode.toLowerCase().equals("current") && args.length < 1) return false;



        if (mode.toLowerCase().equals("add")) {

            String location = args[2];
            String targetPlayerUsername = args[1];
            String level = args[3].toLowerCase();

            if (!level.equalsIgnoreCase("owner") && !level.equalsIgnoreCase("member")) {

                sender.sendMessage(ChatColor.RED + "Level can only be owner or member!");
                return true;

            }

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

                LocalPlayer targetPlayer = EasyGuard.getWorldGuard().wrapPlayer(Bukkit.getPlayer(targetPlayerUsername));

                if (level.equalsIgnoreCase("owner")) {

                    owners.addPlayer(targetPlayer);
                    region.setOwners(owners);

                } else {

                    DefaultDomain members = region.getMembers();
                    members.addPlayer(targetPlayer);
                    region.setMembers(members);

                }

                sender.sendMessage(ChatColor.GREEN + targetPlayerUsername + " was added to " + region.getId());

            } catch (Exception e) {

                sender.sendMessage(ChatColor.RED + "Not found!");

                EasyGuard.getPlugin().getLogger().info(e.toString());

            }

        }

        if (mode.toLowerCase().equals("remove")) {

            String location = args[2];
            String targetPlayerUsername = args[1];

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
                DefaultDomain members = region.getMembers();

                if (!owners.contains(localPlayer)) {

                    sender.sendMessage(ChatColor.RED + "You cannot do that!");
                    return true;

                }

                LocalPlayer targetPlayer = EasyGuard.getWorldGuard().wrapPlayer(Bukkit.getPlayer(targetPlayerUsername));

                if (targetPlayerUsername.equalsIgnoreCase(player.getDisplayName())) {

                    sender.sendMessage(ChatColor.RED + "You cannot do that!");
                    return true;

                }

                owners.removePlayer(targetPlayer);
                members.removePlayer(targetPlayer);

                region.setOwners(owners);
                region.setMembers(members);

                sender.sendMessage(ChatColor.GREEN + targetPlayerUsername + " was removed from " + region.getId());

            } catch (Exception e) {

                sender.sendMessage(ChatColor.RED + "Not found!");

                EasyGuard.getPlugin().getLogger().info(e.toString());

            }

        }

        if (mode.toLowerCase().equals("list")) {

            String location = args[1];

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
                DefaultDomain members = region.getMembers();

                String ownersString = this.toUserFriendlyStringConvertUUID(owners.toUserFriendlyString());
                String membersString = this.toUserFriendlyStringConvertUUID(members.toUserFriendlyString());

                if (!owners.contains(localPlayer)) {

                    sender.sendMessage(ChatColor.RED + "You cannot do that!");
                    return true;

                }

                sender.sendMessage(String.format("%s%s%s has the following players trusted:", ChatColor.GOLD, region.getId(), ChatColor.GREEN));
                sender.sendMessage(String.format("%sOwners: %s%s", ChatColor.BLUE, ChatColor.GOLD, ownersString));
                sender.sendMessage(String.format("%sMembers: %s%s", ChatColor.BLUE, ChatColor.GOLD, membersString));

            } catch (Exception e) {

                sender.sendMessage(ChatColor.RED + "Not found!");

                EasyGuard.getPlugin().getLogger().info(e.toString());

            }

        }

        if (mode.toLowerCase().equals("current")) {

            try {

                Location targetLocaiton = player.getLocation();

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
                    DefaultDomain members = region.getMembers();

                    String ownersString = this.toUserFriendlyStringConvertUUID(owners.toUserFriendlyString());
                    String membersString = this.toUserFriendlyStringConvertUUID(members.toUserFriendlyString());

                    sender.sendMessage(String.format("%s%s%s has the following players trusted:", ChatColor.GOLD, region.getId(), ChatColor.GREEN));
                    sender.sendMessage(String.format("%sOwners: %s%s", ChatColor.BLUE, ChatColor.GOLD, ownersString));
                    sender.sendMessage(String.format("%sMembers: %s%s", ChatColor.BLUE, ChatColor.GOLD, membersString));

                }

            } catch (Exception e) {

                sender.sendMessage(ChatColor.RED + "Not found!");

                EasyGuard.getPlugin().getLogger().info(e.toString());

            }

        }

        return true;
    }

    private String toUserFriendlyStringConvertUUID(String ufs) {

        String[] parts = ufs.split(",");
        String out = "[";

        for (String item : parts) {

            if (Pattern.compile("^uuid:", Pattern.CASE_INSENSITIVE).matcher(item).find() || Pattern.compile("^.uuid:", Pattern.CASE_INSENSITIVE).matcher(item).find()) {

                String[] currentParts = item.split(":");

                if (currentParts.length < 2) return null;

                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(currentParts[1]));

                out += player.getName() + ",";

            } else {

                out += item + ",";

            }

        }

        if (out.endsWith(",")) {

            char[] outchars = out.toCharArray();

            outchars[out.lastIndexOf(",")] = ']';

            out = new String(outchars);

        }

        return out;

    }

}
