package com.clancraft.turnmanager.shield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.clancraft.turnmanager.*;

/**
 * Bukkit Runnable class that checks every player's location every predetermined
 * interval, compares each location with the current player, and decides if
 * the location violates the predetermined shield boundary.
 */
public class PositionChecker implements Runnable, ShieldPublisher {
    /**
     * Map that stores previous valid coordinates for each players.
     */
    private static HashMap<String, PlayerCoordinate> coordinateMap = new HashMap<>();

    /**
     * Array that stores the subscriber to shield events.
     */
    private static ArrayList<ShieldSubscriber> subscriberList = new ArrayList<>();

    /**
     * Default constructor. Creates and populates the coordinate map.
     */
    public PositionChecker() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Location loc = player.getLocation();
            coordinateMap.put(player.getName(), new PlayerCoordinate(loc.getX(), loc.getY(), loc.getZ()));
        });
    }

    /**
     * Every time it's scheduled, scans all player's position. If a player is
     * within SHIELD_RADIUS of the active player, their position is reverted
     * back to the last known valid position.
     */
    @Override
    public void run() {
        if (TurnManager.getCycle().size() <= 0) {
            return;
        }

        Player currPlayer = null;
        Iterator<? extends Player> playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            Player p = playerIter.next();
            if (p.getName().equals(TurnManager.getCycle().currentPlayer())) {
                currPlayer = p;
            }
        }

        if (currPlayer == null || !TurnManager.getShield().isActive()) {
            return;
        }

        playerIter = Bukkit.getOnlinePlayers().iterator();
        while (playerIter.hasNext()) {
            Player player = playerIter.next();
            if (!TurnManager.getShield().isInShield(player.getName())) {
                continue;
            }

            Location loc = player.getLocation();
            Double distSqr = Math.pow(loc.getX() - currPlayer.getLocation().getX(), 2)
                    + Math.pow(loc.getY() - currPlayer.getLocation().getY(), 2);

            if (distSqr < Math.pow(TMConstants.SHIELD_RADIUS, 2)) {
                loc.setX(coordinateMap.get(player.getName()).x);
                loc.setY(coordinateMap.get(player.getName()).y);
                loc.setZ(coordinateMap.get(player.getName()).z);
                publishShieldBreach(player);
            } else {
                coordinateMap.put(player.getName(), new PlayerCoordinate(loc.getX(), loc.getY(), loc.getZ()));
            }
        }
    }

    @Override
    public void registerShieldSubscriber(ShieldSubscriber sub){
        subscriberList.add(sub);
    }

    @Override
    public boolean removeShieldSubscriber(ShieldSubscriber sub){
        return subscriberList.remove(sub);
    }

    /**
     * Notifies the subscriber that the specifies player breaches currently
     * active shield.
     * 
     * @param player Player that breaches active shield
     */
    @Override
    public void publishShieldBreach(Player player){
        subscriberList.forEach(sub -> {
            sub.notifyShieldBreach(player);
        });
    }

    /**
     * Inner class to store the x, y, z coordinates of a player
     */
    class PlayerCoordinate {
        public double x, y, z;

        public PlayerCoordinate(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
