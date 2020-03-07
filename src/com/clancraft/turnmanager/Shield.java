package com.clancraft.turnmanager;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A class to handle shield functionality.
 */
public class Shield {
	
	private ArrayList<String> shieldList;
	private boolean isToggled;
	
	/**
     * Default constructor. Initialises the fields.
     */
	public Shield() {
		shieldList = new ArrayList<String>();
		isToggled = false;
	}
	
	/**
	 * Adds a player to the shield list.
	 * @param playerName player to be added to the shield list
	 * @return true if successful, false if unsuccessful
	 */
	public boolean addPlayer(String playerName) {
		// checks whether player already exists inside the list
        for (String s : shieldList) {
            if (s.toLowerCase().equals(playerName.toLowerCase())) {
                return false;
            }
        }
        
        shieldList.add(playerName);
        return true;
	}
	
	/**
	 * Adds all online players to the shield list.
	 * TODO use addPlayer method here?
	 */
	public void addAllPlayers() {
		Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            shieldList.add(playerIter.next().getName());
        }
	}
	
	/**
	 * Removes a player from the shield list.
	 * @param playerName player to be removed from the list
	 * @return true if successful, false if unsuccessful
	 */
	public boolean removePlayer(String playerName) {
		for (int i = 0; i < shieldList.size(); i++) {
            if (shieldList.get(i).toLowerCase().equals(playerName.toLowerCase())) {
                shieldList.remove(i);
                return true;
            }
        }
        return false;
	}
	
	/**
	 * Empties the shield list.
	 */
	public void clearShield() {
		shieldList.clear();
	}
	
	/**
     * Returns a string representation of the shield list.
     * @return players in the shield list
     * TODO improve string format?
     */
    public String toString() {
    	StringBuilder turnSequence = new StringBuilder();
        turnSequence.append(shieldList.get(0)); // guaranteed to have index 0
        
        for (int i = 1; i < shieldList.size(); i++) {
            turnSequence.append(", " + shieldList.get(i));
        }
        
        return turnSequence.toString();
    }
	
	/**
	 * Getter for whether the shield is toggled on or not.
	 * @return true if toggled on, false if toggled off.
	 */
	public boolean getIsToggled() {
		return isToggled;
	}
	
	/**
	 * Setter to toggle the shield on or off.
	 * @param isToggled on or off.
	 */
	public void setIsToggled(boolean isToggled) {
		this.isToggled = isToggled;
	}

}
