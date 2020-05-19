package com.clancraft.turnmanager.shield;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.clancraft.turnmanager.*;

/**
 * Class to handle teleportation.
 */
public class Teleport {
	
	/**
	 * Sets gamemode to SPECTATOR and teleports player to the current player.
	 * @param player player who executed the command
	 */
	public boolean teleport(Player player) {
		// disallow teleportation if player is in the current player's shield list
        if (TurnManager.getShield().isInShield(player.getName())) {
			// TODO send player message that they are shielded
			return false;
		}
		
		String currPlayerName = TurnManager.getCycle().currentPlayer();
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
		
		if (currPlayer == null) {
			// TODO send player message that the current turn's player could not be found
			return false;
		}
		
        // set player gamemode to SPECTATOR before teleporting
        player.setGameMode(GameMode.SPECTATOR);
		player.teleport(currPlayer);
		return true;
	}
	
}
