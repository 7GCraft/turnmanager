package com.clancraft.turnmanager;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.clancraft.turnmanager.turn.*;
import com.clancraft.turnmanager.shield.*;
import com.clancraft.turnmanager.calendar.*;

/**
 * Main driver class of the TurnManager plugin.
 */
public class TurnManager extends JavaPlugin {
    /**
     * Static object representing this plugin.
     */
    private static JavaPlugin plugin;

    /**
     * Static Cycle object associated with this plugin.
     */
    private static Cycle cycle;

    /**
     * Static Turn object associated with this plugin.
     */
    private static Turn turn;

    /**
     * Static Teleport object associated with this plugin.
     */
    private static Teleport teleport;
    /**
     * Static Shield object associated with this plugin.
     */
    private static Shield shield;

    /**
     * Static Calendar object associated with this plugin.
     */
    private static Calendar calendar;

    /**
     * Per Bukkit API, runs when server is starting up.
     */
    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        plugin = this;
        cycle = new Cycle();
        turn = new Turn();
        teleport = new Teleport();
        shield = new Shield();
        calendar = new Calendar(this);

        shield.loadShieldData();
        calendar.loadCalendarData();

        registerCommands();

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    /**
     * Per Bukkit API, runs when server is shutting down.
     */
    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        shield.writeShieldData();
        calendar.writeCalendarData();

        logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
    }

    /**
     * Registers the command "tm" to the server.
     */
    private void registerCommands() {
        getCommand("tm").setExecutor(new TMCommandHandler());
    }

    /**
     * Gets this plugin's object.
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets this plugin's Cycle object
     *
     * @return Cycle object for the plugin
     */
    public static Cycle getCycle() {
        return cycle;
    }

    /**
     * Gets this plugin's Turn object
     *
     * @return Cycle object for the plugin
     */
    public static Turn getTurn() {
        return turn;
    }

    /**
     * Gets this plugin's Teleport object
     *
     * @return Teleport object for the plugin
     */
    public static Teleport getTeleport() {
        return teleport;
    }

    /**
     * Gets this plugin's Shield object
     *
     * @return Shield object for the plugin
     */
    public static Shield getShield() {
        return shield;
    }

    /**
     * Gets this plugin's Calendar object
     *
     * @return Calendar object for the plugin
     */
    public static Calendar getCalendar() {
        return calendar;
    }
}
