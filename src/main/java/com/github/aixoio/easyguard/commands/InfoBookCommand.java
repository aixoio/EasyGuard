package com.github.aixoio.easyguard.commands;

import com.github.aixoio.easyguard.util.InfoBooks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoBookCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("easyguard.info-book")) {

            sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command!");
            return true;

        }

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        player.openBook(InfoBooks.getPlayerBook());

        if (sender.hasPermission("easyguard.info-book-admin")) {

            player.getInventory().addItem(InfoBooks.getPlayerBook());

        }

        return true;

    }

}
