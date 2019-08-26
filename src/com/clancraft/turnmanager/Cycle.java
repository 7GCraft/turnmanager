package com.clancraft.turnmanager;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cycle {
    private ArrayList<String> playerList;
    private int currPlayerIndex;

    public Cycle() {
    	playerList = new ArrayList<String>();
        //TODO get all the names of all the online players
    }

    public boolean addPlayer(String playerName) {
        // checks whether player already exists inside the list
        for (String s : playerList) {
            if (s.toLowerCase().equals(playerName.toLowerCase())) {
                return false;
            }
        }

        String validatedName = validatePlayerName(playerName);
        if (validatedName == null) {
            return false;
        }

        playerList.add(validatedName);
        return true;
    }

    public boolean addPlayer(String playerName, int order) {
        // checks whether player already exists inside the list
        for (String s : playerList) {
            if (s.toLowerCase().equals(playerName.toLowerCase())) {
                return false;
            }
        }

        String validatedName = validatePlayerName(playerName);
        if (validatedName == null) {
            return false;
        }

        playerList.add(order, validatedName);
        return true;
    }

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

    public boolean removePlayer(String playerName) {
        return playerList.remove(playerName);
    }

    public boolean removePlayer(int spot) {
        try {
            playerList.remove(spot);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    public boolean swap(String playerName1, String playerName2) {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).equals(playerName1)) {
                index1 = i;
            }
            if (playerList.get(i).equals(playerName2)) {
                index2 = i;
            }
        }

        if (index1 == -1 || index2 == -1) {
            return false;
        }

        if (index1 < currPlayerIndex && index2 > currPlayerIndex || 
            index2 < currPlayerIndex && index1 > currPlayerIndex) {
            return false;
        }

        String tempString = playerList.get(index1);
        playerList.set(index1, playerList.get(index2));
        playerList.set(index2, tempString);

        return true;
    } 

    public void announceTurn() {
        String turnSequence = getTurnSequence();   
    
        Bukkit.broadcastMessage(String.format(TMStrings.CURRENT_PLAYER_ANNOUNCE, playerList.get(currPlayerIndex), String.format(TMStrings.PLAYER_LIST, turnSequence)));
    }
    
    //TODO what does this do? Put the last person back in the queue?
    public boolean reinstatePlayer() {
    	return false;
    }

    public void nextTurn() {
        currPlayerIndex = (currPlayerIndex + 1) % playerList.size();
        announceTurn();
    }
    
    public String getTurnSequence() {
    	StringBuilder turnSequence = new StringBuilder();
    	
        for (String s : playerList) {
            turnSequence.append( s + " -> ");
        }
        turnSequence.append("END OF CYCLE");
        
        return turnSequence.toString();
    }

    //TODO timer functionality
    
    //TODO add functionality where it automatically skips players who's not present
}