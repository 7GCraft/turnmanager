package com.clancraft.turnmanager;

import org.bukkit.ChatColor;

/**
 * A class to store String prompts for all other methods.
 */
public class TMStrings {
    public static final String PLUGIN_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "TurnManager" + ChatColor.DARK_RED + "]" + ChatColor.GOLD + " ";

    public static final String MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + "/tm requires an argument!\n" + 
                                                        PLUGIN_PREFIX + "/tm [argument]";
    public static final String INVALID_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED + "Invalid argument.";
    public static final String NO_PERMISSION_ERROR = PLUGIN_PREFIX + ChatColor.RED + "You don't have permission to do that.";

    public static final String PLAYER_LIST = PLUGIN_PREFIX + "%s";

    public static final String ADD_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been added to the turn sequence.";
    public static final String ADD_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s could not be added to the turn sequence.";

    public static final String REMOVE_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been removed from the turn sequence.";
    public static final String REMOVE_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s could not be removed from the turn sequence.";

    public static final String SWAP_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s and %s have been swapped in the turn sequence.";
    public static final String SWAP_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED + "%s and %s could not be swapped in the turn sequence.";

    public static final String CURRENT_PLAYER_ANNOUNCE = PLUGIN_PREFIX + "Current player is: %s\n" + "%s\n";

    public static final String TIMER_INITIAL = "Turn has started. %d minutes remaining in this turn.";
    public static final String TIMER_COUNTDOWN = "%d minutes remaining in this turn.";
    public static final String TIMER_TIMEUP = "The time for this turn is up!";
    public static final String TIMER_OVERTIME = "This turn is %d minutes over time!";
    
    // PERMISSIONS
    // TO-DO: make separate class?
    public static final String CYCLE_PERMISSION = "tm.cycle";
    public static final String CYCLE_ADD_PERMISSION = CYCLE_PERMISSION + ".add";
    public static final String CYCLE_LIST_PERMISSION = CYCLE_PERMISSION + ".list";
    public static final String CYCLE_REMOVE_PERMISSION = CYCLE_PERMISSION + ".remove";
    public static final String CYCLE_SWAP_PERMISSION = CYCLE_PERMISSION + ".swap";
    
    public static final String TURN_PERMISSION = "tm.turn";
    public static final String TURN_NEXT_PERMISSION = TURN_PERMISSION + ".next";
    public static final String TURN_ANNOUNCE_PERMISSION = TURN_PERMISSION + ".announce";
}