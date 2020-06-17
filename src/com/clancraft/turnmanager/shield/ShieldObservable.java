package com.clancraft.turnmanager.shield;

import org.bukkit.entity.Player;

/**
 * Interface for observables/subjects in the Shield observer pattern.
 */
public interface ShieldObservable {
    /**
     * Adds the observer to the observer list
     * 
     * @param obs observer to be added to the observer list
     */
    public void registerShieldObserver(ShieldObserver obs);
    
    /**
     * Removes the observer from the observer list
     * 
     * @param obs observer to be removed from the observer list
     */
    public boolean removeShieldObserver(ShieldObserver obs);

    /**
     * Notifies all observers in the observer list
     */
    public void notifyShieldBreach(Player player);
}