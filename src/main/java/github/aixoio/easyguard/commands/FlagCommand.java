package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.flag")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        if (args.length == 0) return false;


        String mode = args[0];

        if (mode.toLowerCase().equals("list")) {

            sender.sendMessage(ChatColor.GREEN + "You can add the following flags to your claims:");

            for (String flagName : this.getFlagsNames()) {

                sender.sendMessage(String.format("%s- %s%s", ChatColor.RESET, ChatColor.LIGHT_PURPLE, flagName));

            }

        } else if (mode.equalsIgnoreCase("add")) {

            // TODO: Add the add flag sub-command

        } else if (mode.equalsIgnoreCase("remove")) {

            // TODO: Add the remove flag sub-command

        }

        return true;

    }

    public StateFlag[] getFlags() {

        StateFlag[] flags = {

                Flags.PASSTHROUGH,
                Flags.BUILD,
                Flags.BLOCK_BREAK,
                Flags.BLOCK_PLACE,
                Flags.USE,
                Flags.INTERACT,
                Flags.DAMAGE_ANIMALS,
                Flags.PVP,
                Flags.SLEEP,
                Flags.RESPAWN_ANCHORS,
                Flags.TNT,
                Flags.CHEST_ACCESS,
                Flags.PLACE_VEHICLE,
                Flags.DESTROY_VEHICLE,
                Flags.LIGHTER,
                Flags.RIDE,
                Flags.POTION_SPLASH,
                Flags.ITEM_FRAME_ROTATE,
                Flags.TRAMPLE_BLOCKS,
                Flags.FIREWORK_DAMAGE,
                Flags.USE_ANVIL,
                Flags.USE_DRIPLEAF,
                Flags.ITEM_PICKUP,
                Flags.ITEM_DROP,
                Flags.EXP_DROPS,
                Flags.MOB_DAMAGE,
                Flags.CREEPER_EXPLOSION,
                Flags.ENDERDRAGON_BLOCK_DAMAGE,
                Flags.GHAST_FIREBALL,
                Flags.OTHER_EXPLOSION,
                Flags.WITHER_DAMAGE,
                Flags.ENDER_BUILD,
                Flags.SNOWMAN_TRAILS,
                Flags.RAVAGER_RAVAGE,
                Flags.ENTITY_PAINTING_DESTROY,
                Flags.ENTITY_ITEM_FRAME_DESTROY,
                Flags.MOB_SPAWNING,
                Flags.PISTONS,
                Flags.FIRE_SPREAD,
                Flags.LAVA_FIRE,
                Flags.LIGHTNING,
                Flags.SNOW_FALL,
                Flags.SNOW_MELT,
                Flags.ICE_FORM,
                Flags.ICE_MELT,
                Flags.FROSTED_ICE_MELT,
                Flags.FROSTED_ICE_FORM,
                Flags.MUSHROOMS,
                Flags.LEAF_DECAY,
                Flags.GRASS_SPREAD,
                Flags.MYCELIUM_SPREAD,
                Flags.VINE_GROWTH,
                Flags.ROCK_GROWTH,
                Flags.CROP_GROWTH,
                Flags.SOIL_DRY,
                Flags.CORAL_FADE,
                Flags.WATER_FLOW,
                Flags.LAVA_FLOW,
                Flags.SEND_CHAT,
                Flags.RECEIVE_CHAT,
                Flags.INVINCIBILITY,
                Flags.FALL_DAMAGE,
                Flags.HEALTH_REGEN,
                Flags.HUNGER_DRAIN,
                Flags.ENTRY,
                Flags.EXIT,
                Flags.EXIT_VIA_TELEPORT,
                Flags.ENDERPEARL,
                Flags.CHORUS_TELEPORT,

        };

        return flags;

    }

    public String[] getFlagsNames() {

        String[] flags = {

                "PASSTHROUGH",
                "BUILD",
                "BLOCK_BREAK",
                "BLOCK_PLACE",
                "USE",
                "INTERACT",
                "DAMAGE_ANIMALS",
                "PVP",
                "SLEEP",
                "RESPAWN_ANCHORS",
                "TNT",
                "CHEST_ACCESS",
                "PLACE_VEHICLE",
                "DESTROY_VEHICLE",
                "LIGHTER",
                "RIDE",
                "POTION_SPLASH",
                "ITEM_FRAME_ROTATE",
                "TRAMPLE_BLOCKS",
                "FIREWORK_DAMAGE",
                "USE_ANVIL",
                "USE_DRIPLEAF",
                "ITEM_PICKUP",
                "ITEM_DROP",
                "EXP_DROPS",
                "MOB_DAMAGE",
                "CREEPER_EXPLOSION",
                "ENDERDRAGON_BLOCK_DAMAGE",
                "GHAST_FIREBALL",
                "OTHER_EXPLOSION",
                "WITHER_DAMAGE",
                "ENDER_BUILD",
                "SNOWMAN_TRAILS",
                "RAVAGER_RAVAGE",
                "ENTITY_PAINTING_DESTROY",
                "ENTITY_ITEM_FRAME_DESTROY",
                "MOB_SPAWNING",
                "PISTONS",
                "FIRE_SPREAD",
                "LAVA_FIRE",
                "LIGHTNING",
                "SNOW_FALL",
                "SNOW_MELT",
                "ICE_FORM",
                "ICE_MELT",
                "FROSTED_ICE_MELT",
                "FROSTED_ICE_FORM",
                "MUSHROOMS",
                "LEAF_DECAY",
                "GRASS_SPREAD",
                "MYCELIUM_SPREAD",
                "VINE_GROWTH",
                "ROCK_GROWTH",
                "CROP_GROWTH",
                "SOIL_DRY",
                "CORAL_FADE",
                "WATER_FLOW",
                "LAVA_FLOW",
                "SEND_CHAT",
                "RECEIVE_CHAT",
                "INVINCIBILITY",
                "FALL_DAMAGE",
                "HEALTH_REGEN",
                "HUNGER_DRAIN",
                "ENTRY",
                "EXIT",
                "EXIT_VIA_TELEPORT",
                "ENDERPEARL",
                "CHORUS_TELEPORT",

        };

        return flags;

    }

}
