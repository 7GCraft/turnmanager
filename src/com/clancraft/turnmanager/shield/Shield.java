package com.clancraft.turnmanager.shield;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.clancraft.turnmanager.*;

/**
 * A class to handle shield functionality.
 */
public class Shield {
    private HashMap<String, ShieldData> shieldHashMap;
    private ShieldPublisher shieldBreachPublisher;

    private File shieldConfigFile;
    private FileConfiguration shieldConfig;

    /**
     * Default constructor. Initialises the fields.
     */
    public Shield() {
        shieldHashMap = new HashMap<>();

        Plugin plugin = TurnManager.getPlugin();
        shieldConfigFile = new File(plugin.getDataFolder(), TMConstants.SHIELDS_CONFIG_FILE_NAME);

        if (!shieldConfigFile.exists()) {
            shieldConfigFile.getParentFile().mkdirs();
            plugin.saveResource("shields.yml", false);
        }

        shieldConfig = YamlConfiguration.loadConfiguration(shieldConfigFile);

        PositionChecker positionChecker = new PositionChecker();
        shieldBreachPublisher = positionChecker;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, 
                positionChecker,
                TMConstants.TICKS_IN_SECOND,
                TMConstants.POSITION_CHECKER_INTERVAL_SECONDS * TMConstants.TICKS_IN_SECOND);
    }

    public ShieldPublisher getShieldBreachPublisher() {
        return shieldBreachPublisher;
    }

    /**
     * Adds a player to the current player's shield list.
     *
     * @param playerName player to be added to the shield list
     * @return true if successful, false if unsuccessful
     */
    public boolean addPlayer(String playerName) {
        return addPlayer(TurnManager.getCycle().currentPlayer(), playerName);
    }

    /**
     * Adds a player to a specified player's shield list.
     *
     * @param shieldPlayerName player whose shield list is being added to
     * @param playerToAddName  player to be added to the shield list
     * @param true             if successful, false if unsuccessful
     */
    public boolean addPlayer(String shieldPlayerName, String playerToAddName) {
        HashSet<String> shieldList = shieldHashMap.get(shieldPlayerName).getShieldList();
        // checks whether player already exists inside the list
        if (shieldList.contains(playerToAddName)) {
            return false;
        }

        shieldList.add(playerToAddName);
        return true;
    }

    /**
     * Adds all online players to the current player's shield list.
     */
    public void addAllPlayers() {
        addAllPlayers(TurnManager.getCycle().currentPlayer());
    }

    /**
     * Adds all online players to a specified player's shield list.
     *
     * @param playerName player whose shield list gets added with all players
     */
    public void addAllPlayers(String playerName) {
        clearShield(playerName);

        Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            addPlayer(playerName, playerIter.next().getName());
        }
    }

    /**
     * Removes a player from the current player's shield list.
     *
     * @param playerName player to be removed from the list
     * @return true if successful, false if unsuccessful
     */
    public boolean removePlayer(String playerName) {
        return removePlayer(TurnManager.getCycle().currentPlayer(), playerName);
    }

    /**
     * Removes a player from a specified player's shield list.
     *
     * @param shieldPlayerName player whose shield list is being removed from
     * @param playerName       player to be removed from the list
     * @return true if successful, false if unsuccessful
     */
    public boolean removePlayer(String shieldPlayerName, String playerName) {
        HashSet<String> shieldList = shieldHashMap.get(shieldPlayerName).getShieldList();
        shieldList.remove("playerName");
        return false;
    }

    /**
     * Empties the current player's shield list.
     */
    public void clearShield() {
        clearShield(TurnManager.getCycle().currentPlayer());
    }

    /**
     * Empties a specified player's shield list.
     *
     * @param playerName player whose shield list is to be cleared
     */
    public void clearShield(String playerName) {
        shieldHashMap.get(playerName).getShieldList().clear();
    }

    public boolean isInShield(String playerName) {
        return isInShield(TurnManager.getCycle().currentPlayer(), playerName);
    }

    public boolean isInShield(String playerShieldList, String playerName) {
        return shieldHashMap.get(playerShieldList).getShieldList().contains(playerName);
    }

    public boolean registerPlayer(String playerName) {
        if (shieldHashMap.containsKey(playerName)) {
            return false;
        }

        ShieldData data = new ShieldData(true, new HashSet<String>());
        shieldHashMap.put(playerName, data);
        return true;
    }

    public boolean unregisterPlayer(String playerName) {
        if (!shieldHashMap.containsKey(playerName)) {
            return false;
        }

        shieldHashMap.remove(playerName);
        return true;
    }

    /**
     * Returns a string representation of the shield list.
     *
     * @return players in the shield list
     */
    public String printShieldList(String playerName) {
        StringBuilder sb = new StringBuilder("You are shielding your turn from these players:\n");

        HashSet<String> shieldList = shieldHashMap.get(playerName).getShieldList();
        PriorityQueue<String> heap = new PriorityQueue<>();

        shieldList.forEach(shieldedPlayer -> {
            heap.add(shieldedPlayer);
        });

        while (heap.peek() != null) {
            sb.append(heap.poll() + "\n");
        }

        return sb.toString();
    }

    /**
     * Returns a string representation of the shield list.
     *
     * @return players in the shield list
     */
    public String printShieldList() {
        return printShieldList(TurnManager.getCycle().currentPlayer());
    }

    /**
     * Toggles the current player's shield on or off.
     *
     * @param isToggled shield on or off
     */
    public void toggle(boolean isToggled) {
        toggle(TurnManager.getCycle().currentPlayer(), isToggled);
    }

    /**
     * Toggles a specified player's shield on or off.
     *
     * @param playerName player whose shield is being toggled
     * @param isToggled  shield on or off
     */
    public void toggle(String playerName, boolean isToggled) {
        shieldHashMap.get(playerName).setIsToggled(isToggled);
    }

    /**
     * @return whether the current player's shield is on
     */
    public boolean isActive() {
        return shieldHashMap.get(TurnManager.getCycle().currentPlayer()).isToggled;
    }

    public void loadShieldData() {
        List<String> playerList = this.getShieldConfig().getStringList("playerlist");
        Bukkit.getLogger().info("playerList: " + playerList);
        playerList.forEach(playerName -> {
            boolean isToggled = this.getShieldConfig().getBoolean("shields." + playerName + ".toggle");
            Bukkit.getLogger().info("isToggled: " + isToggled);

            HashSet<String> shieldList = new HashSet<>();
            List<String> list = this.getShieldConfig().getStringList("shields." + playerName + ".list");
            Bukkit.getLogger().info("list: " + list);
            list.forEach(shieldName -> {
                shieldList.add(shieldName);
            });
            Bukkit.getLogger().info("shieldList: " + shieldList);

            ShieldData data = new ShieldData(isToggled, shieldList);
            shieldHashMap.put(playerName, data);
            Bukkit.getLogger().info(shieldHashMap.get(playerName).getShieldList().toString());
        });
    }

    public void writeShieldData() {
        List<String> playerList = new ArrayList<String>();
        shieldHashMap.forEach((playerName, playerShieldData) -> {
            playerList.add(playerName);
            Bukkit.getLogger().info("playerList: " + playerList);

            List<String> shieldList = new ArrayList<String>();
            playerShieldData.getShieldList().forEach(k -> {
                shieldList.add(k);
            });
            Bukkit.getLogger().info("shieldList: " + shieldList);

            this.getShieldConfig().set("shields." + playerName + ".toggle", playerShieldData.isToggled());
            this.getShieldConfig().set("shields." + playerName + ".list", shieldList);

            Bukkit.getLogger().info("getConfig().set() calls executed.");
        });

        this.getShieldConfig().set("playerlist", playerList);

        this.saveShieldConfig();
    }

    private FileConfiguration getShieldConfig() {
        return this.shieldConfig;
    }

    private void saveShieldConfig() {
        try {
            this.shieldConfig.save(shieldConfigFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to save shield data!");
        }
    }

    class ShieldData {
        private boolean isToggled;
        private HashSet<String> shieldList;

        public ShieldData(boolean isToggled, HashSet<String> shieldList) {
            this.isToggled = isToggled;
            this.shieldList = shieldList;
        }

        public boolean isToggled() {
            return isToggled;
        }

        public void setIsToggled(boolean isToggled) {
            this.isToggled = isToggled;
        }

        public HashSet<String> getShieldList() {
            return shieldList;
        }
    }

}
