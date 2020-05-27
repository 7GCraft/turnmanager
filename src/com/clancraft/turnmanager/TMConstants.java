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

    public static final int TICKS_IN_SECOND = 20;
    public static final int TICKS_IN_MINUTE = 1200;

    public static final int POSITION_CHECKER_INTERVAL_SECONDS = 5;
    public static final int LOGOUT_LISTENER_INTERVAL_MINUTES = 20;
    
    public static final Double SHIELD_RADIUS = 65.00;
}