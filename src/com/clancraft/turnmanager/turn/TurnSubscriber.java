package com.clancraft.turnmanager.turn;

/**
 * Interface for observers in the Turn observer pattern.
 */
public interface TurnSubscriber {
    /**
     * Gets called by the observable/subject to update observer.
     */
    public void updateTurnIncrement();
}