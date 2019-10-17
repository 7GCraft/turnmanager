package com.clancraft.turnmanager;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class TurnManager extends JavaPlugin {
    protected static Cycle cycle;
    protected static Turn turn;
	
    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        registerCommands();
        
        cycle = new Cycle();
        turn = new Turn();

        logger.info(pdfFile.getName() + " has been enabled! (v." + pdfFile.getVersion() + ")");
    }

    public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled! (v." + pdfFile.getVersion() + ")");
    }
    
    private void registerCommands() {
        getCommand("tm").setExecutor(new TMCommandHandler());
    }
}
