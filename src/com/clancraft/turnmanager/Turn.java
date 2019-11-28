package com.clancraft.turnmanager;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Class to handle Cycle input and output
 * TODO make class static
 */
public class Turn {
    private TurnTimer timer;

    /**
     * Announces the current player's turn along with the entire sequence.
     */
    public void announceTurn() {
        String currPlayer = TurnManager.cycle.currentPlayer();
        String turnSequence = TurnManager.cycle.toString();   
    
        Bukkit.broadcastMessage(String.format(TMConstants.ANNOUNCE_CURRENT_PLAYER, currPlayer));
        Bukkit.broadcastMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, turnSequence));
    }
    
    //TODO what does this do? Put the last person back in the queue?
    public boolean reinstatePlayer() {
    	return false;
    }

    /**
     * Advances Cycle until it finds an available player. Then, automatically
     * announce that player's turn.
     */
    public void nextTurn() {
        // while next player is not available
        for (int i = 0; !checkPlayerAvailability(TurnManager.cycle.next()); i++) {
            if (i >= TurnManager.cycle.size()) {
                Bukkit.broadcastMessage("No player in the cycle is currently present!");
                return;
            }
        }

        announceTurn();
        stopTimer();
        startTimer();
    }

    /**
     * Helper method to check whether player is currently available
     * A player is available if the player is online
     * 
     * @param input name of the player to be checked
     * @return whether player specified is available
     */
    private boolean checkPlayerAvailability(String input) {
        Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            String currPlayer = playerIter.next().getName();
            if (input.toLowerCase().equals(currPlayer.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Starts the timer with the default value of 15 minutes.
     */
    public void startTimer() {
        startTimer(15);
    }

    /**
     * Starts the timer for the specified minute
     * 
     * @param minute minutes to be counted
     */
    public void startTimer(int minute) {
        if (timer != null) {
            timer.halt();
        }

        timer = new TurnTimer(minute);
        timer.start();
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        if (timer != null) {
            timer.halt();
            timer = null;
        }
    }
}