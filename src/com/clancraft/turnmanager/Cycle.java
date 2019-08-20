package com.clancraft.turnmanager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cycle {
    private ArrayList<String> playerList;
    private int currPlayerIndex;

    private final String ANNOUNCE_FORMAT = "Current player is: %s\n" + "%s\n";

    public void init() {
    	playerList = new ArrayList<String>();
        //get all the names of all the online players
    }
    
    public String listPlayers() {
        StringBuilder turnSequence = new StringBuilder();
        for (String s : playerList) {
            turnSequence.append( s + " -> ");
        }
        turnSequence.append("END OF CYCLE");
        
        return turnSequence.toString();
    }

    public boolean addPlayer(String playerName) {
        if (!playerList.contains(playerName)) {
            playerList.add(playerName.toLowerCase());
            return true;
        }
        //TODO: check whether player name is valid or not
        return false;
    }

    public boolean removePlayer(String playerName) {
        return playerList.remove(playerName);
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
        StringBuilder turnSequence = new StringBuilder();
        for (String s : playerList) {
            turnSequence.append( s + " -> ");
        }
        turnSequence.append("END OF CYCLE");        
    
        Bukkit.broadcastMessage(String.format(ANNOUNCE_FORMAT, playerList.get(currPlayerIndex), turnSequence.toString()));
    }

    public void nextTurn() {
        currPlayerIndex = (currPlayerIndex + 1) % playerList.size();
        announceTurn();
    }

    //TODO timer functionality
    
}