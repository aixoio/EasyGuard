package com.github.aixoio.easyguard.commands;

import com.github.aixoio.easyguard.EasyGuard;
import com.github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.LinkedList;

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

        try {

            LinkedList<SQLiteClaimData> claims = EasyGuard.SQLITE_MANAGER.getClaims(player.getUniqueId().toString());

            if (claims.size() == 0) {

                sender.sendMessage(ChatColor.RED + "You do not have any claims!");
                return true;

            }

            sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "You own the following claims:");

            for (SQLiteClaimData claim : claims) {

                sender.sendMessage(String.format("%s- %s%s", ChatColor.DARK_GRAY, ChatColor.GOLD, claim.getName()));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;

    }
}
