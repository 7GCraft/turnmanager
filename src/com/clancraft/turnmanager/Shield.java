package com.clancraft.turnmanager;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A class to handle shield functionality.
 */
public class Shield {

    private HashMap<String, ShieldData> shieldHashMap;

    /**
     * Default constructor. Initialises the fields.
     */
    public Shield() {

    }

    /**
     * Adds a player to the current player's shield list.
     * 
     * @param playerName player to be added to the shield list
     * @return true if successful, false if unsuccessful
     */
    public boolean addPlayer(String playerName) {
        return addPlayer(TurnManager.cycle.currentPlayer(), playerName);
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
        addAllPlayers(TurnManager.cycle.currentPlayer());
    }

    /**
     * Adds all online players to a specified player's shield list.
     * 
     * @param playerName player whose shield list is added all players to
     */
    // TODO shorten the param description
    public void addAllPlayers(String playerName) {
        clearShield(playerName);

        HashSet<String> shieldList = shieldHashMap.get(playerName).getShieldList();
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
        return removePlayer(TurnManager.cycle.currentPlayer(), playerName);
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
        clearShield(TurnManager.cycle.currentPlayer());
    }

    /**
     * Empties a specified player's shield list.
     * 
     * @param playerName player whose shield list is to be cleared
     */
    public void clearShield(String playerName) {
        shieldHashMap.get(playerName).getShieldList().clear();
    }

    /**
     * Returns a string representation of the shield list.
     * 
     * @return players in the shield list TODO improve string format?
     */
    public String toString() {
        HashSet<String> shieldList = shieldHashMap.get(TurnManager.cycle.currentPlayer()).getShieldList();
        StringBuilder turnSequence = new StringBuilder();
        // turnSequence.append(shieldList.get(0)); // guaranteed to have index 0

        for (int i = 1; i < shieldList.size(); i++) {
            // turnSequence.append(", " + shieldList.get(i));
        }

        return turnSequence.toString();
    }

    /**
     * Toggles the current player's shield on or off.
     * 
     * @param isToggled shield on or off
     */
    public void toggle(boolean isToggled) {
        toggle(TurnManager.cycle.currentPlayer(), isToggled);
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

    public void writeShieldData(JavaPlugin plugin) {
        List<String> playerList = new ArrayList<String>();
        shieldHashMap.forEach((playerName, playerShieldData) -> {
            playerList.add(playerName);

            List<String> shieldList = new ArrayList<String>();
            playerShieldData.getShieldList().forEach(k -> {
                shieldList.add(k);
            });

            plugin.getConfig().set("shields." + playerName + ".toggle", playerShieldData.isToggled());
            plugin.getConfig().set("shields." + playerName + ".list", shieldList);
        });

        plugin.getConfig().set("playerlist", playerList);
    }
}
