package com.clancraft.turnmanager;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main driver class of the TurnManager plugin
 */
// TODO method Javadocs
public class TurnManager extends JavaPlugin {
    public static final int TICKS_IN_SECOND = 20;
    public static final int POSITION_CHECKER_INTERVAL = 5;

    protected static Cycle cycle;
    protected static Turn turn;
    protected static Teleport teleport;
    protected static Shield shield;

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        registerCommands();

        cycle = new Cycle();
        turn = new Turn();
        teleport = new Teleport();
        shield = new Shield(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PositionChecker(), TICKS_IN_SECOND,
                POSITION_CHECKER_INTERVAL * TICKS_IN_SECOND);

        shield.loadShieldData();

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        shield.writeShieldData();

        logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
    }

    private void registerCommands() {
        getCommand("tm").setExecutor(new TMCommandHandler());
    }
}
