package com.clancraft.turnmanager;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Class to handle teleportation.
 */
public class Teleport {
	
	/**
	 * Sets gamemode to SPECTATOR and teleports player to the current player.
	 * @param player player who executed the command
	 */
	public void teleport(Player player) {
		String currPlayerName = TurnManager.cycle.currentPlayer();
		Player currPlayer = null;
	
		// cached reference
		Player tempPlayer = null;
		
		Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
		// iterate through all players and find current player
        while (playerIter.hasNext()) {
        	tempPlayer = playerIter.next();
        	
        	if (tempPlayer.getName().equalsIgnoreCase(currPlayerName)) {
        		currPlayer = tempPlayer;
        		break;
        	}
        }
		
        // set player gamemode to SPECTATOR before teleporting
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(currPlayer);
	}
	
}
