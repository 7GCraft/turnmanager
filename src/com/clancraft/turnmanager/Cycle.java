package com.clancraft.turnmanager;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cycle {
    private ArrayList<String> playerList;
    private int currIndex;

    /**
     * Default constructor. Initializes the fields to zero-like values. 
     */
    public Cycle() {
        playerList = new ArrayList<String>();
        currIndex = -1;
    }

    public String pop() {
        currIndex = (currIndex + 1) % playerList.size();
        try {
            return playerList.get(currIndex);
        } catch (IndexOutOfBoundsException e) {
            //TODO put an error message in logger here
            return null;
        }
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

        if (order <= currIndex) {
            currIndex++;
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
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).toLowerCase().equals(playerName.toLowerCase())) {
                playerList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(int spot) {
        try {
            playerList.remove(spot);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        if (spot <= currIndex) {
            currIndex--;
        }

        return true;
    }

    public boolean swap(String playerName1, String playerName2) {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).toLowerCase().equals(playerName1.toLowerCase())) {
                index1 = i;
            }
            if (playerList.get(i).toLowerCase().equals(playerName2.toLowerCase())) {
                index2 = i;
            }
        }

        if (index1 == -1 || index2 == -1) {
            return false;
        }

        return swap(index1, index2);
    } 

    public boolean swap(int index1, int index2) {
        if (index1 < currIndex && index2 > currIndex || 
            index2 < currIndex && index1 > currIndex) {
            return false;
        }

        String tempString = playerList.get(index1);
        playerList.set(index1, playerList.get(index2));
        playerList.set(index2, tempString);

        return true;
    }

    public int size() {
        return playerList.size();
    }

    public String getPlayerName(int spot) {
        return playerList.get(spot);
    }

    public String toString() {
    	StringBuilder turnSequence = new StringBuilder();
    	
        for (String s : playerList) {
            turnSequence.append( s + " -> ");
        }
        turnSequence.append("END OF CYCLE");
        
        return turnSequence.toString();
    }
}