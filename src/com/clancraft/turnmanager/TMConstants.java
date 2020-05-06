package com.clancraft.turnmanager;

import org.bukkit.ChatColor;

/**
 * A class to store String prompts for all other methods.
 */
public class TMConstants {
    public static final String PLUGIN_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "TurnManager"
            + ChatColor.DARK_RED + "]" + ChatColor.GOLD + " ";

    public static final String MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + "/tm requires an argument!\n" + PLUGIN_PREFIX
            + "/tm [argument]";
    public static final String INVALID_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED + "Invalid argument.";
    public static final String NO_PERMISSION_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "You don't have permission to do that.";

    public static final String ADD_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been added to the turn sequence.";
    public static final String ADD_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED
            + "%s could not be added to the turn sequence.";

    public static final String REMOVE_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been removed from the turn sequence.";
    public static final String REMOVE_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED
            + "%s could not be removed from the turn sequence.";

    public static final String SWAP_PLAYER_SUCCESS = PLUGIN_PREFIX
            + "%s and %s have been swapped in the turn sequence.";
    public static final String SWAP_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED
            + "%s and %s could not be swapped in the turn sequence.";

    public static final String ANNOUNCE_CURRENT_PLAYER = PLUGIN_PREFIX + "Current player is: %s";
    public static final String ANNOUNCE_SEQUENCE = PLUGIN_PREFIX + "Sequence: %s";

    public static final String TIMER_INITIAL = "Turn has started.";
    public static final String TIMER_COUNTDOWN = "%d minutes remaining in this turn.";
    public static final String TIMER_TIMEUP = "The time for this turn is up!";
    public static final String TIMER_OVERTIME = "This turn is %d minutes over time!";
    
    public static final String SHIELDS_CONFIG_FILE_NAME = "shields.yml";
    
    public static final String CALENDAR_CONFIG_FILE_NAME = "calendar.yml";

    // PERMISSIONS
    // TODO make separate class?
    public static final String CYCLE_PERMISSION = "tm.cycle";
    public static final String CYCLE_ADD_PERMISSION = CYCLE_PERMISSION + ".add";
    public static final String CYCLE_LIST_PERMISSION = CYCLE_PERMISSION + ".list";
    public static final String CYCLE_REMOVE_PERMISSION = CYCLE_PERMISSION + ".remove";
    public static final String CYCLE_SWAP_PERMISSION = CYCLE_PERMISSION + ".swap";

    public static final String TURN_PERMISSION = "tm.turn";
    public static final String TURN_NEXT_PERMISSION = TURN_PERMISSION + ".next";
    public static final String TURN_ANNOUNCE_PERMISSION = TURN_PERMISSION + ".announce";

    public static final String TIMER_PERMISSION = "tm.timer";
    public static final String TIMER_START_PERMISSION = TIMER_PERMISSION + ".start";
    public static final String TIMER_STOP_PERMISSION = TIMER_PERMISSION + ".stop";
    public static final String TIMER_PAUSE_PERMISSION = TIMER_PERMISSION + ".pause";
    public static final String TIMER_RESUME_PERMISSION = TIMER_PERMISSION + ".resume";
    
    public static final String SHIELD_PERMISSION = "tm.shield";
    public static final String SHIELD_TOGGLE_PERMISSION = SHIELD_PERMISSION + ".toggle";
    public static final String SHIELD_ON_PERMISSION = SHIELD_PERMISSION + ".on";
    public static final String SHIELD_OFF_PERMISSION = SHIELD_PERMISSION + ".off";
    public static final String SHIELD_ALL_PERMISSION = SHIELD_PERMISSION + ".all";
    public static final String SHIELD_ADD_PERMISSION = SHIELD_PERMISSION + ".add";
    public static final String SHIELD_REMOVE_PERMISSION = SHIELD_PERMISSION + ".remove";
    public static final String SHIELD_CLEAR_PERMISSION = SHIELD_PERMISSION + ".clear";
    public static final String SHIELD_LIST_PERMISSION = SHIELD_PERMISSION + ".list";
}