package com.clancraft.turnmanager.turn;

import java.util.ArrayList;
import java.util.Iterator;

import com.clancraft.turnmanager.exception.DuplicatePlayerException;
import com.clancraft.turnmanager.exception.InvalidArgumentException;
import com.clancraft.turnmanager.exception.PlayerNotFoundException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A class to hold a cycle of players. This class is similar to queue that 
 * always loops back to the front. This cycle is specifically written
 * to store Minecraft player names.
 */
public class Cycle {
    public static final String BREAK_NAME = "Break";

    /**
     * List containing players in the cycle.
     */
    private ArrayList<String> playerList;

    /**
     * Index of the current player.
     */
    private int currIndex;

    /**
     * Default constructor. Initializes the fields to zero-like values. 
     */
    public Cycle() {
        playerList = new ArrayList<String>();
        playerList.add(BREAK_NAME);
        currIndex = 0;
    }

    /**
     * Returns the current player.
     * 
     * @return name of the current player
     */
    public String currentPlayer() {
        return playerList.get(currIndex);
    }

    /**
     * Advances the internal state of the cycle, returns the next player.
     * 
     * @return name of the next player
     */
    public String next() {
        currIndex = (currIndex + 1) % playerList.size();
        return currentPlayer();
    }

    /**
     * Adds the specified player to the end of the queue.
     * 
     * @param playerName name of the player to be added
     * @return whether player was added successfully
     */
    public void addPlayer(String playerName) throws 
            DuplicatePlayerException, PlayerNotFoundException {
        try {
            addPlayer(playerName, size());
        } catch (InvalidArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Adds the specified player to the specified spot.
     * 
     * @param playerName name of the player to be added
     * @param spot where in the cycle to add the player. Has to be between 0
     *             and cycle size.
     */
    public void addPlayer(String playerName, int spot) throws 
            InvalidArgumentException, DuplicatePlayerException, PlayerNotFoundException {
        if (spot < 0 || spot > size()) {
            throw new InvalidArgumentException();
        }

        // checks whether player already exists inside the list
        for (String s : playerList) {
            if (s.toLowerCase().equals(playerName.toLowerCase())) {
                throw new DuplicatePlayerException();
            }
        }

        String validatedName = validatePlayerName(playerName);
        if (validatedName == null) {
            throw new PlayerNotFoundException();
        }

        if (spot <= currIndex) {
            currIndex++;
        }

        playerList.add(spot, validatedName);
    }

    /**
     * Helper method to validate player name to be added.
     * 
     * @param input name of the player to be validated
     * @return player name with proper capitalization
     */
    private String validatePlayerName(String input) throws PlayerNotFoundException {
        Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            String currPlayer = playerIter.next().getName();
            if (input.toLowerCase().equals(currPlayer.toLowerCase())) {
                return currPlayer;
            }
        }
        
        throw new PlayerNotFoundException();
    }

    /**
     * Removes the specified player from the cycle.
     * 
     * @param playerName name of the player to be removed
     */
    public void removePlayer(String playerName) throws PlayerNotFoundException {
        for (int i = 0; i < size(); i++) {
            if (playerList.get(i).toLowerCase().equals(playerName.toLowerCase())) {
                try {
                    removePlayer(i);
                    return;
                } catch (InvalidArgumentException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        
        throw new PlayerNotFoundException();
    }

    /**
     * Removes a player on the specified spot from the cycle.
     * 
     * @param spot where in the list to remove player from. Has to be between 
     *             0 and cycle size.
     * @return whether player was removed successfully
     */
    public void removePlayer(int spot) throws InvalidArgumentException {
        if (spot < 0 || spot >= size()) {
            throw new InvalidArgumentException();
        }

        playerList.remove(spot);

        if (spot <= currIndex) {
            currIndex--;
        }
    }

    /**
     * Swaps the two specified players' spots in the cycle.
     * 
     * @param playerName1 name of the first player to be swapped
     * @param playerName2 name of the second player to be swapped
     * @return whether the players were swapped successfully
     */
    public void swap(String playerName1, String playerName2) throws PlayerNotFoundException {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < size(); i++) {
            if (playerList.get(i).toLowerCase().equals(playerName1.toLowerCase())) {
                index1 = i;
            }
            if (playerList.get(i).toLowerCase().equals(playerName2.toLowerCase())) {
                index2 = i;
            }
        }

        if (index1 == -1 || index2 == -1) {
            throw new PlayerNotFoundException();
        }

        try {
            swap(index1, index2);
        } catch (InvalidArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    } 

    /**
     * Swaps the players in the two specified spots in the cycle.
     * 
     * @param index1 spot of the first player to be swapped. Has to be between 
     *               0 and cycle size.
     * @param index2 spot of the second player to be swapped. Has to be between 
     0*              0 and cycle size.
     * @return whether the players were swapped successfully
     */
    public void swap(int index1, int index2) throws InvalidArgumentException {
        if (index1 < 0 || index1 >= size()) {
            throw new InvalidArgumentException();
        }

        if (index2 < 0 || index2 >= size()) {
            throw new InvalidArgumentException();
        }

        if (index1 < currIndex && index2 > currIndex || 
            index2 < currIndex && index1 > currIndex) {
            throw new InvalidArgumentException();
        }

        String tempString = playerList.get(index1);
        playerList.set(index1, playerList.get(index2));
        playerList.set(index2, tempString);
    }

    /**
     * Returns the number of players currently in the cycle.
     * 
     * @return number of players in the cycle
     */
    public int size() {
        return playerList.size() - 1; // - 1 to account for "Break"
    }

    /**
     * Returns the name of the player in the specified spot.
     * 
     * @return name of the player in the spot. Has to be between 0 and cycle
     *         size.
     */
    public String getPlayerName(int spot) throws InvalidArgumentException {
        if (spot < 0 || spot >= size()) {
            throw new InvalidArgumentException();
        }

        return playerList.get(spot);
    }

    /**
     * Returns a string representation of the cycle, which is the order of
     * the players in the cycle.
     * 
     * @return order of the players in the cycle
     */
    public String toString() {
    	StringBuilder turnSequence = new StringBuilder();
        turnSequence.append(playerList.get(0)); // guaranteed to have index 0
        
        for (int i = 1; i < playerList.size(); i++) {
            turnSequence.append(" -> " + playerList.get(i));
        }
        
        return turnSequence.toString();
    }
}