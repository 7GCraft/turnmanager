package com.clancraft.turnmanager.calendar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.clancraft.turnmanager.turn.TurnSubscriber;
import com.clancraft.turnmanager.*;
import com.clancraft.turnmanager.exception.DateSyncException;
import com.clancraft.turnmanager.exception.DuplicatePlayerException;
import com.clancraft.turnmanager.exception.PlayerNotFoundException;

/**
 * A class to handle Date functionality.
 */
public class Calendar implements TurnSubscriber {

    private HashMap<String, Date> playerDates;
    private Date worldDate;
    private boolean isAuto;

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

        TurnManager.getTurn().registerTurnSubscriber(this);
    }

    public void loadCalendarData() {
        String worldDateString = this.getCalendarConfig().getString("world-date");
        String[] worldDateStringParts = worldDateString.split("-");
        int worldDay = Integer.parseInt(worldDateStringParts[0]);
        Date.Month worldMonth = Date.getMonthEnum(Integer.parseInt(worldDateStringParts[1]));
        int worldYear = Integer.parseInt(worldDateStringParts[2]);

        worldDate = new Date(worldDay, worldMonth, worldYear, false);

        isAuto = this.getCalendarConfig().getBoolean("auto");

        List<String> playerList = this.getCalendarConfig().getStringList("playerlist");

        playerList.forEach(playerName -> {
            String dateString = this.getCalendarConfig().getString("dates." + playerName + ".date");
            String[] dateStringParts = dateString.split("-");
            int day = Integer.parseInt(dateStringParts[0]);
            Date.Month month = Date.getMonthEnum(Integer.parseInt(worldDateStringParts[1]));
            int year = Integer.parseInt(dateStringParts[2]);

            boolean isSynced = this.getCalendarConfig().getBoolean("dates." + playerName + ".sync");

            Date date = new Date(day, month, year, isSynced);

            playerDates.put(playerName, date);
        });
    }

    public void writeCalendarData() {
        this.getCalendarConfig().set("world-date", worldDate.getDay() + "-" + worldDate.getMonth().monthNum + "-" + worldDate.getYear());

        this.getCalendarConfig().set("auto", isAuto);

        List<String> playerList = new ArrayList<String>();

        for (String playerName : playerDates.keySet()) {
            playerList.add(playerName);
        }

        this.getCalendarConfig().set("playerlist", playerList);

        playerDates.forEach((playerName, playerDate) -> {
            this.getCalendarConfig().set("dates." + playerName + ".date", playerDate.getDay() + "-" + playerDate.getMonth().monthNum + "-" + playerDate.getYear());
            this.getCalendarConfig().set("dates." + playerName + ".sync", playerDate.getIsSynced());
        });

        this.saveCalendarConfig();
    }

    /**
     * Advances player's date by specified number of days.
     *
     * @param playerName   player's date to be advanced
     * @param daysToAdd    days to advance
     */
    public void addPlayerDate(String playerName, int daysToAdd) throws DateSyncException {
        Date playerDate = playerDates.get(playerName);

        if (playerDate.getIsSynced()) {
            throw new DateSyncException();
        }

        playerDate.add(daysToAdd);
    }

    /**
     * Advances world date and all synced dates.
     * @param daysToAdd    days to advance
     */
    public void addWorldDate(int daysToAdd) {
        worldDate.add(daysToAdd);

        // advance all synced player dates
        syncPlayerDates();
    }

    /**
     * Sets a specified player's date.
     * @param playerName
     * @param day
     * @param month
     * @param year
     */
    public void setPlayerDate(String playerName, int day, int month, int year) throws DateSyncException {
        Date playerDate = playerDates.get(playerName);

        if (playerDate.getIsSynced()) {
            throw new DateSyncException();
        }

        playerDate.setDate(day, Date.Month.values()[month], year);
    }

    /**
     * Sets the world date and all synced dates.
     * @param day
     * @param month
     * @param year
     */
    public void setWorldDate(int day, int month, int year) {
        worldDate.setDate(day, Date.Month.values()[month - 1], year);

        // set all synced player dates
        syncPlayerDates();
    }

    /**
     * Syncs a specified player's date to the world date.
     * @param playerName
     */
    public void sync(String playerName) {
        Date playerDate = playerDates.get(playerName);

        playerDate.setIsSynced(true);
    }

    /**
     * Unsyncs a specified player's date to the world date.
     * @param playerName
     */
    public void unsync(String playerName) {
        Date playerDate = playerDates.get(playerName);

        playerDate.setIsSynced(false);
    }

    public void registerPlayer(String playerName) throws DuplicatePlayerException {
        if (playerDates.containsKey(playerName)) {
            throw new DuplicatePlayerException();
        }

        Date date = new Date();
        playerDates.put(playerName, date);
    }

    public void unregisterPlayer(String playerName) throws PlayerNotFoundException {
        if (!playerDates.containsKey(playerName)) {
            throw new PlayerNotFoundException();
        }

        playerDates.remove(playerName);
    }

    /**
     * Returns a specified player's date.
     * @param playerName
     * @return  specified player's current date
     */
    public String getPlayerDate(String playerName) {
        Date playerDate = playerDates.get(playerName);

        return playerDate.getDate();
    }

    /**
     * Returns the world date.
     * @return  current world date
     */
    public String getWorldDate() {
        return worldDate.getDate();
    }

    /**
     * Advances the world date and all synced player dates by one day.
     */
    public void advanceWorldDate() {
        addWorldDate(1);
    }

    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    /**
     * Syncs all synced player dates to the world date.
     */
    private void syncPlayerDates() {
        playerDates.forEach((playerName, playerDate) -> {
            if (playerDate.getIsSynced()) {
                playerDate.setDate(worldDate.getDay(), worldDate.getMonth(), worldDate.getYear());
            }
        });
    }

    private FileConfiguration getCalendarConfig() {
        return calendarConfig;
    }

    private void saveCalendarConfig() {
        try {
            this.calendarConfig.save(calendarConfigFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to save calendar data!");
        }
    }

    @Override
    public void updateTurnIncrement() {
        if (isAuto) {
            advanceWorldDate();
        }
    }
}
