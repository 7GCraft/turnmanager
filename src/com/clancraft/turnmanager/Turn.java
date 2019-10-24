package com.clancraft.turnmanager;

import org.bukkit.Bukkit;

public class Turn {
    public void announceTurn(String currPlayer) {
        String turnSequence = TurnManager.cycle.toString();   
    
        Bukkit.broadcastMessage(String.format(TMStrings.CURRENT_PLAYER_ANNOUNCE, currPlayer, String.format(TMStrings.PLAYER_LIST, turnSequence)));
    }
    
    //TODO what does this do? Put the last person back in the queue?
    public boolean reinstatePlayer() {
    	return false;
    }

    public void nextTurn() {
        String currPlayer = TurnManager.cycle.pop();

        if (currPlayer != null) {
            announceTurn(currPlayer);
        }
    }

    //TODO timer functionality
    
    //TODO add functionality where it automatically skips players who's not present
}