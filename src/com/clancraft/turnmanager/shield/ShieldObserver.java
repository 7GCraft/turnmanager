package com.clancraft.turnmanager.shield;

import org.bukkit.entity.Player;

/**
 * Interface for observers in the Shield observer pattern.
 */
public interface ShieldObserver {
    /**
     * Gets called by the observable/subject to update observer.
     */
    public void updateShieldBreach(Player player);
}