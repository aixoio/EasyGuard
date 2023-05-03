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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            if (args.length < 3) return false;

            String inFlag = args[1].toUpperCase();
            String location = args[2];

            ArrayList<String> flagsNames = new ArrayList<String>(Arrays.asList(this.getFlagsNames()));

            if (!flagsNames.contains(inFlag)) {

                sender.sendMessage(ChatColor.RED + "Flag not found!");

            } else {

                try {

                    Location targetLocaiton = EasyGuard.getPlugin().getConfig().getLocation(String.format("data.%s.%s.location", player.getDisplayName(), location));

                    if (targetLocaiton == null) {

                        sender.sendMessage(ChatColor.RED + "Not found!");
                        return true;

                    }

                    StateFlag flagToAdd = this.stringToFlag(inFlag);

                    if (flagToAdd == null) {

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

                        if (!owners.contains(localPlayer)) continue;

                        region.setFlag(flagToAdd, StateFlag.State.ALLOW);

                        sender.sendMessage(ChatColor.GREEN + "Add flag to " + region.getId());

                    }

                } catch (Exception e) {

                    sender.sendMessage(ChatColor.RED + "Not found!");

                    EasyGuard.getPlugin().getLogger().info(e.toString());

                }

            }

        } else if (mode.equalsIgnoreCase("remove")) {

            if (args.length < 3) return false;

            String inFlag = args[1].toUpperCase();
            String location = args[2];

            ArrayList<String> flagsNames = new ArrayList<String>(Arrays.asList(this.getFlagsNames()));

            if (!flagsNames.contains(inFlag)) {

                sender.sendMessage(ChatColor.RED + "Flag not found!");

            } else {

                try {

                    Location targetLocaiton = EasyGuard.getPlugin().getConfig().getLocation(String.format("data.%s.%s.location", player.getDisplayName(), location));

                    if (targetLocaiton == null) {

                        sender.sendMessage(ChatColor.RED + "Not found!");
                        return true;

                    }

                    StateFlag flagToAdd = this.stringToFlag(inFlag);

                    if (flagToAdd == null) {

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

                        if (!owners.contains(localPlayer)) continue;

                        region.setFlag(flagToAdd, StateFlag.State.DENY);

                        sender.sendMessage(ChatColor.GREEN + "Removed flag from " + region.getId());

                    }

                } catch (Exception e) {

                    sender.sendMessage(ChatColor.RED + "Not found!");

                    EasyGuard.getPlugin().getLogger().info(e.toString());

                }

            }

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

    public StateFlag stringToFlag(String str) {

        switch (str) {

            case "PASSTHROUGH":

                return Flags.PASSTHROUGH;

            case "BUILD":

                return Flags.BUILD;

            case "BLOCK_BREAK":

                return Flags.BLOCK_BREAK;

            case "BLOCK_PLACE":

                return Flags.BLOCK_PLACE;

            case "USE":

                return Flags.USE;

            case "INTERACT":

                return Flags.INTERACT;

            case "DAMAGE_ANIMALS":

                return Flags.DAMAGE_ANIMALS;

            case "PVP":

                return Flags.PVP;

            case "SLEEP":

                return Flags.SLEEP;

            case "RESPAWN_ANCHORS":

                return Flags.RESPAWN_ANCHORS;

            case "TNT":

                return Flags.TNT;

            case "CHEST_ACCESS":

                return Flags.CHEST_ACCESS;

            case "PLACE_VEHICLE":

                return Flags.PLACE_VEHICLE;

            case "DESTROY_VEHICLE":

                return Flags.DESTROY_VEHICLE;

            case "LIGHTER":

                return Flags.LIGHTER;

            case "RIDE":

                return Flags.RIDE;

            case "POTION_SPLASH":

                return Flags.POTION_SPLASH;

            case "ITEM_FRAME_ROTATE":

                return Flags.ITEM_FRAME_ROTATE;

            case "TRAMPLE_BLOCKS":

                return Flags.TRAMPLE_BLOCKS;

            case "FIREWORK_DAMAGE":

                return Flags.FIREWORK_DAMAGE;

            case "USE_ANVIL":

                return Flags.USE_ANVIL;

            case "USE_DRIPLEAF":

                return Flags.USE_DRIPLEAF;

            case "ITEM_PICKUP":

                return Flags.ITEM_PICKUP;

            case "ITEM_DROP":

                return Flags.ITEM_DROP;

            case "EXP_DROPS":

                return Flags.EXP_DROPS;

            case "MOB_DAMAGE":

                return Flags.MOB_DAMAGE;

            case "CREEPER_EXPLOSION":

                return Flags.CREEPER_EXPLOSION;

            case "ENDERDRAGON_BLOCK_DAMAGE":

                return Flags.ENDERDRAGON_BLOCK_DAMAGE;

            case "GHAST_FIREBALL":

                return Flags.GHAST_FIREBALL;

            case "OTHER_EXPLOSION":

                return Flags.OTHER_EXPLOSION;

            case "WITHER_DAMAGE":

                return Flags.WITHER_DAMAGE;

            case "ENDER_BUILD":

                return Flags.ENDER_BUILD;

            case "SNOWMAN_TRAILS":

                return Flags.SNOWMAN_TRAILS;

            case "RAVAGER_RAVAGE":

                return Flags.RAVAGER_RAVAGE;

            case "ENTITY_PAINTING_DESTROY":

                return Flags.ENTITY_PAINTING_DESTROY;

            case "ENTITY_ITEM_FRAME_DESTROY":

                return Flags.ENTITY_ITEM_FRAME_DESTROY;

            case "MOB_SPAWNING":

                return Flags.MOB_SPAWNING;

            case "PISTONS":

                return Flags.PISTONS;

            case "FIRE_SPREAD":

                return Flags.FIRE_SPREAD;

            case "LAVA_FIRE":

                return Flags.LAVA_FIRE;

            case "LIGHTNING":

                return Flags.LIGHTNING;

            case "SNOW_FALL":

                return Flags.SNOW_FALL;

            case "SNOW_MELT":

                return Flags.SNOW_MELT;

            case "ICE_FORM":

                return Flags.ICE_FORM;

            case "ICE_MELT":

                return Flags.ICE_MELT;

            case "FROSTED_ICE_MELT":

                return Flags.FROSTED_ICE_MELT;

            case "FROSTED_ICE_FORM":

                return Flags.FROSTED_ICE_FORM;

            case "MUSHROOMS":

                return Flags.MUSHROOMS;

            case "LEAF_DECAY":

                return Flags.LEAF_DECAY;

            case "GRASS_SPREAD":

                return Flags.GRASS_SPREAD;

            case "MYCELIUM_SPREAD":

                return Flags.MYCELIUM_SPREAD;

            case "VINE_GROWTH":

                return Flags.VINE_GROWTH;

            case "ROCK_GROWTH":

                return Flags.ROCK_GROWTH;

            case "CROP_GROWTH":

                return Flags.CROP_GROWTH;

            case "SOIL_DRY":

                return Flags.SOIL_DRY;

            case "CORAL_FADE":

                return Flags.CORAL_FADE;

            case "WATER_FLOW":

                return Flags.WATER_FLOW;

            case "LAVA_FLOW":

                return Flags.LAVA_FLOW;

            case "SEND_CHAT":

                return Flags.SEND_CHAT;

            case "RECEIVE_CHAT":

                return Flags.RECEIVE_CHAT;

            case "INVINCIBILITY":

                return Flags.INVINCIBILITY;

            case "FALL_DAMAGE":

                return Flags.FALL_DAMAGE;

            case "HEALTH_REGEN":

                return Flags.HEALTH_REGEN;

            case "HUNGER_DRAIN":

                return Flags.HUNGER_DRAIN;

            case "ENTRY":

                return Flags.ENTRY;

            case "EXIT":

                return Flags.EXIT;

            case "EXIT_VIA_TELEPORT":

                return Flags.EXIT_VIA_TELEPORT;

            case "ENDERPEARL":

                return Flags.ENDERPEARL;

            case "CHORUS_TELEPORT":

                return Flags.CHORUS_TELEPORT;

            default:
                return null;


        }

    }

}
