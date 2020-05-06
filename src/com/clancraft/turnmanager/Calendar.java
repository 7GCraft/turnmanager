package com.clancraft.turnmanager;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class to handle Date functionality.
 */
public class Calendar {
	
	private HashMap<String, Date> playerDates;
	private Date worldDate;
	
	private File calendarConfigFile;
	private FileConfiguration calendarConfig;
	
	/**
	 * Default constructor. Initialises fields.
	 * @param plugin reference to plugin
	 */
	public Calendar(JavaPlugin plugin) {
	    playerDates = new HashMap<>();
		
	    calendarConfigFile = new File(plugin.getDataFolder(), TMConstants.CALENDAR_CONFIG_FILE_NAME);
        
        if (!calendarConfigFile.exists()) {
            calendarConfigFile.getParentFile().mkdirs();
            plugin.saveResource(TMConstants.CALENDAR_CONFIG_FILE_NAME, false);
        }
        
        calendarConfig = YamlConfiguration.loadConfiguration(calendarConfigFile);
	}
	
	public void loadCalendarData() {
	    String worldDateString = this.getCalendarConfig().getString("world-date");
	    String[] worldDateStringParts = worldDateString.split("-");
	    int worldDay = Integer.parseInt(worldDateStringParts[0]);
        int worldMonth = Integer.parseInt(worldDateStringParts[1]);
        int worldYear = Integer.parseInt(worldDateStringParts[2]);
        
        worldDate = new Date(worldDay, worldMonth, worldYear);
	    
	    List<String> playerList = this.getCalendarConfig().getStringList("playerlist");
        
        playerList.forEach(playerName -> {
            String dateString = this.getCalendarConfig().getString("dates." + playerName + ".date");
            String[] dateStringParts = dateString.split("-");
            int day = Integer.parseInt(dateStringParts[0]);
            int month = Integer.parseInt(dateStringParts[1]);
            int year = Integer.parseInt(dateStringParts[2]);
            
            Date date = new Date(day, month, year);

            playerDates.put(playerName, date);
        });
	}
	
	private FileConfiguration getCalendarConfig() {
	    return calendarConfig;
	}
}
