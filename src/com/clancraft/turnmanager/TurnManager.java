package com.clancraft.turnmanager;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.clancraft.turnmanager.turn.*;
import com.clancraft.turnmanager.shield.*;
import com.clancraft.turnmanager.calendar.*;

/**
 * Main driver class of the TurnManager plugin
 */
// TODO method Javadocs
public class TurnManager extends JavaPlugin {
    private static JavaPlugin plugin;
    private static Cycle cycle;
    private static Turn turn;
    private static Teleport teleport;
    private static Shield shield;
    private static Calendar calendar;

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        registerCommands();

        plugin = this;
        cycle = new Cycle();
        turn = new Turn();
        teleport = new Teleport();
        shield = new Shield();
        calendar = new Calendar(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PositionChecker(), TMConstants.TICKS_IN_SECOND,
                TMConstants.POSITION_CHECKER_INTERVAL_SECONDS * TMConstants.TICKS_IN_SECOND);

        shield.loadShieldData();
        calendar.loadCalendarData();

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
    
    public static Cycle getCycle() {
        return cycle;
    }

    public static Turn getTurn() {
        return turn;
    }

    public static Teleport getTeleport() {
        return teleport;
    }

    public static Shield getShield() {
        return shield;
    }

    public static Calendar getCalendar() {
        return calendar;
    }


    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        shield.writeShieldData();
        calendar.writeCalendarData();

        logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
    }

    private void registerCommands() {
        getCommand("tm").setExecutor(new TMCommandHandler());
    }
}
