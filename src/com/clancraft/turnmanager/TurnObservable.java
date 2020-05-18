package com.clancraft.turnmanager;

public interface TurnObservable {
    public void registerTurnObserver(TurnObserver obs);
    
    public boolean removeTurnObserver(TurnObserver obs);

    public void notifyTurnIncrement();
}