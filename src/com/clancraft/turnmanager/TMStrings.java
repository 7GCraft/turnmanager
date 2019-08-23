package com.clancraft.turnmanager;

import org.bukkit.ChatColor;

public class TMStrings {
    public static final String PLUGIN_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "TurnManager" + ChatColor.DARK_RED + "]" + ChatColor.GOLD + " ";

    public static final String MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + "/tm requires an argument!\n" + 
                                                        PLUGIN_PREFIX + "/tm [argument]";

    public static final String PLAYER_LIST = PLUGIN_PREFIX + "%s";

    public static final String ADD_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been added to the turn sequence.";
    public static final String ADD_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s could not be added to the turn sequence.";

    public static final String REMOVE_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been removed from the turn sequence.";
    public static final String REMOVE_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s could not be removed from the turn sequence.";

    public static final String SWAP_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s and %s have been swapped in the turn sequence.";
    public static final String SWAP_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s and %s could not be swapped in the turn sequence.";

    public static final String INVALID_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED + "Invalid argument.";

    public static final String CURRENT_PLAYER_ANNOUNCE = PLUGIN_PREFIX + "Current player is: %s\n" + "%s\n";
}