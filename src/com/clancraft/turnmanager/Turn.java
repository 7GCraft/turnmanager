package com.clancraft.turnmanager;

import org.bukkit.Bukkit;

public class Turn {
    private TurnTimer timer;

    public void announceTurn(String currPlayer) {
        String turnSequence = TurnManager.cycle.toString();   
    
        Bukkit.broadcastMessage(String.format(TMStrings.CURRENT_PLAYER_ANNOUNCE, currPlayer, String.format(TMStrings.PLAYER_LIST, turnSequence)));
    }
    
    //TODO what does this do? Put the last person back in the queue?
    public boolean reinstatePlayer() {
    	return false;
    }

    public void nextTurn() {
        String currPlayer = validatePlayerName(TurnManager.cycle.pop());
        for (int i = 0; currPlayer == null; i++) {
            currPlayer = validatePlayerName(TurnManager.cycle.pop());
            if (i >= TurnManager.cycle.size()) {
                Bukkit.broadcastMessage("No player in the cycle is currently present!");
                return;
            }
        }

        announceTurn(currPlayer);
    }

    /**
     * Helper method to validate player name to be added.
     * 
     * @param input name of the player to be validated
     * @return the actual player name with proper capitalization
     */
    private String validatePlayerName(String input) {
        Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            String currPlayer = playerIter.next().getName();
            if (input.toLowerCase().equals(currPlayer.toLowerCase())) {
                return currPlayer;
            }
        }
        return null;
    }

    public void startTimer() {
        if (timer != null) {
            timer.halt();
        }

        timer = new TurnTimer();
        timer.start();
    }

    public void stopTimer() {
        timer.halt();
        timer = null;
    }
}