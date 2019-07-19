package com.clancraft.turnmanager;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TurnManager extends JavaPlugin {
    
    public void onEnable() {
        PluginDescriptionFile pdfFile = new PluginDescriptionFile();
        Logger logger = getLogger();

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
	}
}
