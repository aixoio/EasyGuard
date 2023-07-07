package com.github.aixoio.easyguard.util;

import com.github.aixoio.easyguard.EasyGuard;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class InfoBooks {

    public static ItemStack getPlayerBook() {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();


        meta.setDisplayName(new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "How to use " + ChatColor.BOLD + "Easy Guard" + ChatColor.RESET + ChatColor.GOLD + " as a player" + ChatColor.DARK_GRAY + "]").getText());
        meta.setTitle("");
        meta.setAuthor("aixoio");

        meta.addPage(ChatColor.GRAY + "[" + ChatColor.GOLD + "How to use " + ChatColor.BOLD + "Easy Guard" + ChatColor.RESET + ChatColor.GOLD + " as a player" + ChatColor.GRAY + "]" +
                ChatColor.DARK_GRAY + "\nBy aixoio on GitHub!");

        meta.addPage(
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Commands:\n" + ChatColor.RESET + "\nTo claim land please use: " + ChatColor.ITALIC + "/claim <name> <x> <y> <z> <x> <y> <z>\n" +
                        ChatColor.RESET + "\nTo see the names of your land claims please use: " + ChatColor.ITALIC + "/claims\n" +
                        ChatColor.RESET + "\nTo access the name of the claim you are currently in please use: " + ChatColor.ITALIC + "/current-claim"
        );

        meta.addPage(

                ChatColor.RESET + "To manage players in a claim please use: " + ChatColor.ITALIC + "/trust [add|remove|list|current] [options]" +
                        "\n/trust add [player] [claim-name] [owner|member]" +
                        "\n/trust remove [player] [claim-name]" +
                        "\n/trust list [claim-name]" +
                        "\n/trust current"

        );


        meta.addPage(

                ChatColor.RESET + "To delete claims please use: " + ChatColor.ITALIC + "/delete-claim" +
                        "\n/remove-claim" +
                        ChatColor.RESET + "\n\nTo manage claim flags please use: " + ChatColor.ITALIC + "/flag [list|add|remove|current|reset] [options]" + ChatColor.RESET + " or " + ChatColor.ITALIC +
                        "/flags [list|add|remove|current|reset] [options]" +
                        "\n/flag list\n/flags list"

        );

        meta.addPage(

                "\n/flag current\n/flags current" +
                        "\n/flag add [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]\n/flags add [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]"

        );

        meta.addPage(

                "\n/flag remove [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]\n/flags remove [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]"

        );

        meta.addPage(

                "\n/flag reset [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]\n/flags reset [flag-name] [claim-name] [owner|member|non-members|non-owners|everyone|none]"

        );

        meta.addPage(

                "To see the bounds (AKA outline) of your current claim please use: " + ChatColor.ITALIC + "/claim-bounds\n" +
                        "\nTo get the location of a claim please use: " + ChatColor.ITALIC + "/where-claim [claim-name]\n"

        );

        meta.addPage(

                "To see info book please use: " + ChatColor.ITALIC + "/claim-help" +
                        "\n/eg-help" +
                        "\n/easy-guard-help" +
                        "\n/guard-help" + "\n\nTo resize a claim please use: " + ChatColor.ITALIC + "/resize-claim <name> <x> <y> <z> <x> <y> <z>"

        );


        Material materialsitck = Material.getMaterial(EasyGuard.getPlugin().getConfig().getString("LOCATION_STICK_ITEM"));

        if (!(materialsitck == null || materialsitck.isAir())) {

            meta.addPage(

                    ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Other:\n" +
                            ChatColor.RESET + "\nTo see you current location please right click any " + materialsitck.toString().replace('_', ' ').toLowerCase()
            );

        }

        book.setItemMeta(meta);

        return book;

    }

}
