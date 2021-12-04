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

    public static final String ADD_PLAYER_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm cycle add requires an argument!\n" + PLUGIN_PREFIX + "/tm cycle add [player]";
    public static final String ADD_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been added to the turn sequence.";
    public static final String ADD_PLAYER_FAILED_DUPLICATE = PLUGIN_PREFIX + ChatColor.RED
            + "%s already exists in the turn sequence.";
    public static final String ADD_PLAYER_FAILED_NOT_FOUND = PLUGIN_PREFIX + ChatColor.RED
            + "%s could not be found.";

    public static final String REMOVE_PLAYER_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm cycle remove requires an argument!\n" + PLUGIN_PREFIX + "/tm cycle remove [player]";
    public static final String REMOVE_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s has been removed from the turn sequence.";
    public static final String REMOVE_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED
            + "%s could not be removed from the turn sequence.";

    public static final String SWAP_PLAYER_SUCCESS = PLUGIN_PREFIX
            + "%s and %s have been swapped in the turn sequence.";
    public static final String SWAP_PLAYER_FAILED = PLUGIN_PREFIX + ChatColor.RED
            + "%s and %s could not be swapped in the turn sequence.";
    public static final String SWAP_PLAYER_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm swap requires two arguments!\n" + PLUGIN_PREFIX + "/tm swap [player1] [player2]";

    public static final String ANNOUNCE_CURRENT_PLAYER = PLUGIN_PREFIX + "Current player is: %s";
    public static final String ANNOUNCE_SEQUENCE = PLUGIN_PREFIX + "Sequence: %s";

    public static final String TURN_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm turn requires an argument!\n" + PLUGIN_PREFIX + "/tm turn [argument]";

    public static final String CYCLE_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm cycle requires an argument!\n" + PLUGIN_PREFIX + "/tm cycle [argument]";

    public static final String TIMER_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm timer requires an argument!\n" + PLUGIN_PREFIX + "/tm timer [argument]";
    public static final String TIMER_INITIAL = PLUGIN_PREFIX + "Turn has started.";
    public static final String TIMER_COUNTDOWN = PLUGIN_PREFIX + "%d minutes remaining in this turn.";
    public static final String TIMER_PAUSE = PLUGIN_PREFIX + "Turn has been suspended.";
    public static final String TIMER_TIMEUP = PLUGIN_PREFIX + "The time for this turn is up!";
    public static final String TIMER_OVERTIME = PLUGIN_PREFIX + "This turn is %d minutes over time!";

    public static final String SHIELD_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm shield requires an argument!\n" + PLUGIN_PREFIX + "/tm shield [argument]";
    public static final String SHIELD_REGISTER_SUCCESS = PLUGIN_PREFIX + "%s has been registered.";
    public static final String SHIELD_PERIMETER_BREACH = PLUGIN_PREFIX + ChatColor.RED
            + "You have entered a shielded area. Your location has been reverted a the previously recorded location.";
    public static final String SHIELD_TELEPORT_BREACH = PLUGIN_PREFIX + ChatColor.RED
            + "Unable to teleport. You are shielded from the current player's turn.";
    public static final String SHIELD_NO_ACTIVE_PLAYER = PLUGIN_PREFIX + ChatColor.RED
            + "Unable to teleport. There is no active player having a turn.";

    public static final String DATE_MISSING_ARGUMENT_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "/tm date requires an argument!\n" + PLUGIN_PREFIX + "/tm date [argument]";
    public static final String DATE_ADD_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s's player date has been moved.";
    public static final String DATE_ADD_WORLD_SUCCESS = PLUGIN_PREFIX + "The world date has been moved.";
    public static final String DATE_SET_PLAYER_SUCCESS = PLUGIN_PREFIX + "%s's player date has been changed.";
    public static final String DATE_SET_WORLD_SUCCESS = PLUGIN_PREFIX + "The world date has been changed.";
    public static final String DATE_SYNC_ERROR = PLUGIN_PREFIX + ChatColor.RED
            + "Unable to change date. %s's date is synced to the world date.";
    public static final String DATE_REGISTER_DUPLICATE = PLUGIN_PREFIX + ChatColor.RED
            + "%s is already registered.";
    public static final String DATE_UNREGISTER_MISSING = PLUGIN_PREFIX + ChatColor.RED
            + "Unable to unregister %s. Player not found";

    public static final String SHIELDS_CONFIG_FILE_NAME = "shields.yml";

    public static final String CALENDAR_CONFIG_FILE_NAME = "calendar.yml";

    public static final int TICKS_IN_SECOND = 20;
    public static final int TICKS_IN_MINUTE = 1200;

    public static final int POSITION_CHECKER_INTERVAL_SECONDS = 5;
    public static final int LOGOUT_LISTENER_INTERVAL_MINUTES = 20;

    public static final Double SHIELD_RADIUS = 65.00;
}