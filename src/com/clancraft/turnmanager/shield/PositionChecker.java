package com.clancraft.turnmanager.shield;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.clancraft.turnmanager.*;

public class PositionChecker implements Runnable {
    HashMap<String, PlayerCoordinate> coordinateMap;

    public PositionChecker() {
        coordinateMap = new HashMap<>();

        Bukkit.getOnlinePlayers().forEach(player -> {
            Location loc = player.getLocation();
            coordinateMap.put(player.getName(), new PlayerCoordinate(loc.getX(), loc.getY(), loc.getZ()));
        });
    }

    /**
     * Scan everyone's position every 5 seconds. If a player is within 65m of the
     * active player, their position is reverted back. Maybe can also send an error
     * message: "You approached a shielded area" TODO
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
            } else {
                coordinateMap.put(player.getName(), new PlayerCoordinate(loc.getX(), loc.getY(), loc.getZ()));
            }
        }
    }

    class PlayerCoordinate {
        public double x, y, z;

        public PlayerCoordinate(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
