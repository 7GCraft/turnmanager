package com.clancraft.turnmanager.shield;

import org.bukkit.entity.Player;

/**
 * Interface for observers in the Shield observer pattern.
 */
public interface ShieldSubscriber {
    /**
     * Gets called by the observable/subject to update observer.
     */
    public void notifyShieldBreach(Player player);
}