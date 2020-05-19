package com.clancraft.turnmanager.turn;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.clancraft.turnmanager.*;

/**
 * Class to listen to a Bukkit PlayerQuitEvent and schedule a player remover.
 */
public class LogoutListener implements Listener {

    /**
     * Inner class to remove players not present in server from the cycle.
     */
    class InactivePlayerRemover implements Runnable {
        /**
         * Player scheduled to be removed.
         */
        Player playerToRemove;

        /**
         * Default constructor
         * 
         * @param playerToRemove player to be removed. Player should ideally not 
         *                       currently be logged in the server.
         */
        public InactivePlayerRemover(Player playerToRemove) {
            this.playerToRemove = playerToRemove;
        }

        /**
         * Checks if player is still missing from the server, then removes 
         * player from the cycle.
         */
        @Override
        public void run() {
            Iterator<? extends Player> iter = Bukkit.getOnlinePlayers().iterator();
            while (iter.hasNext()) {
                Player p = iter.next();
                if (p.getName() == playerToRemove.getName()) {
                    return;
                }
            }

            TurnManager.getCycle().removePlayer(playerToRemove.getName());
        }
    }
    
    /**
     * Registered Bukkit event handler to handle PlayerQuitEvent event. Handler
     * schedules a delayed player remover runnable that runs after
     * TMConstants.LOGOUT_LISTENER_INTERVAL_MINUTES minutes.
     * 
     * @param event the player quit event, as per Bukkit API specifications
     * @see com.clancraft.turnmanager.TMConstants#LOGOUT_LISTENER_INTERVAL_MINUTES
     *      LOGOUT_LISTENER_INTERVAL_MINUTES
     */
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(TurnManager.getPlugin(), 
                new InactivePlayerRemover(event.getPlayer()), 
                TMConstants.LOGOUT_LISTENER_INTERVAL_MINUTES * TMConstants.TICKS_IN_MINUTE);
    }
}