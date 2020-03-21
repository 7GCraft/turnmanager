package com.clancraft.turnmanager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main driver class of the TurnManager plugin
 */
// TODO method Javadocs
public class TurnManager extends JavaPlugin {
    protected static Cycle cycle;
    protected static Turn turn;
    protected static Teleport teleport;
    protected static Shield shield;

    private File shieldsConfigFile;
    private FileConfiguration shieldsConfig;

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        createShieldsConfig();

        registerCommands();

        cycle = new Cycle();
        turn = new Turn();
        teleport = new Teleport();
        shield = new Shield();

        shield.loadShieldData(this);

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        shield.writeShieldData(this);

        logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
    }

    private void registerCommands() {
        getCommand("tm").setExecutor(new TMCommandHandler());
    }

    private void createShieldsConfig() {
        shieldsConfigFile = new File(getDataFolder(), TMConstants.SHIELDS_CONFIG_FILE_NAME);

        if (!shieldsConfigFile.exists()) {
            shieldsConfigFile.getParentFile().mkdirs();
            saveResource(TMConstants.SHIELDS_CONFIG_FILE_NAME, false);
        }

        shieldsConfig = new YamlConfiguration();

        try {
            shieldsConfig.load(shieldsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getShieldsConfig() {
        return this.shieldsConfig;
    }
}
