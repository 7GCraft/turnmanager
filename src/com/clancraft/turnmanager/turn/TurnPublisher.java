package com.clancraft.turnmanager.turn;

/**
 * Interface for observables/subjects in the Turn observer pattern.
 */
public interface TurnPublisher {
    /**
     * Adds the observer to the observer list
     *
     * @param obs observer to be added to the observer list
     */
    public void registerTurnSubscriber(TurnSubscriber obs);

    /**
     * Removes the observer from the observer list
     *
     * @param obs observer to be removed from the observer list
     */
    public boolean removeTurnSubscriber(TurnSubscriber obs);

    /**
     * Notifies all observers in the observer list
     */
    public void notifyTurnIncrement();
}