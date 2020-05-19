package com.clancraft.turnmanager.turn;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.clancraft.turnmanager.*;

public class LogoutListener implements Listener {
    class InactivePlayerRemover implements Runnable {
        Player playerToRemove;

        public InactivePlayerRemover(Player playerToRemove) {
            this.playerToRemove = playerToRemove;
        }

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
    
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(TurnManager.getPlugin(), 
                new InactivePlayerRemover(event.getPlayer()), 
                TMConstants.LOGOUT_LISTENER_INTERVAL_MINUTES * TMConstants.TICKS_IN_MINUTE);
    }
}