package github.aixoio.easyguard.commands;

import github.aixoio.easyguard.EasyGuard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class ClaimsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.claims")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        String playername = player.getDisplayName();

        try {

            Set<String> dataKeys = EasyGuard.getPlugin().getConfig().getConfigurationSection("data." + playername).getKeys(false);


            if (dataKeys.size() == 0) {

                sender.sendMessage(ChatColor.RED + "You do not have any claims!");
                return true;

            }

            sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "You own the following claims");

            for (String key : dataKeys) {

                sender.sendMessage(ChatColor.GOLD + key);

            }

        } catch (Exception e) {

            sender.sendMessage(ChatColor.RED + "You do not have any claims!");
            Bukkit.getServer().getLogger().info(e.getMessage());

        }

        return true;

    }
}
