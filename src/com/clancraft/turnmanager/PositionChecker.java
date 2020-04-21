package com.clancraft.turnmanager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PositionChecker implements Runnable {
    public final Double SHIELD_RADIUS = 65.00;

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
        Player currPlayer = Bukkit.getPlayer(TurnManager.cycle.currentPlayer()); // TODO deprecated method

        Bukkit.getOnlinePlayers().forEach(player -> {
            Location loc = player.getLocation();
            Double distSqr = Math.pow(loc.getX() - currPlayer.getLocation().getX(), 2)
                    + Math.pow(loc.getY() - currPlayer.getLocation().getY(), 2);

            if (distSqr < Math.pow(SHIELD_RADIUS, 2)) {
                loc.setX(coordinateMap.get(player.getName()).x);
                loc.setY(coordinateMap.get(player.getName()).y);
                loc.setZ(coordinateMap.get(player.getName()).z);
            } else {
                coordinateMap.put(player.getName(), new PlayerCoordinate(loc.getX(), loc.getY(), loc.getZ()));
            }
        });
        // TODO make sure the scheduler repeats the task every 5 seconds
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
