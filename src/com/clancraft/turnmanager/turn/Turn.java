package com.clancraft.turnmanager.turn;

import com.clancraft.turnmanager.TMConstants;
import com.clancraft.turnmanager.TurnManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Class to handle Cycle input and output
 */
public class Turn implements TurnPublisher {
    public static final int DEFAULT_TIMER_MINS = 15;
    private TurnTimer timer;
    private final ArrayList<TurnSubscriber> subscriberList;

    public Turn() {
        subscriberList = new ArrayList<>();
    }

    /**
     * Announces the current player's turn along with the entire sequence.
     */
    public void announceTurn() {
        String currPlayer = TurnManager.getCycle().currentPlayer();
        String turnSequence = TurnManager.getCycle().toString();

        Bukkit.broadcastMessage(String.format(TMConstants.ANNOUNCE_CURRENT_PLAYER, currPlayer));
        Bukkit.broadcastMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, turnSequence));
    }

    /**
     * Advances Cycle until it finds an available player. Then, automatically
     * announce that player's turn.
     */
    public void nextTurn() {
        stopTimer();

        // while next player is not available
        for (int i = 0; !checkPlayerAvailability(TurnManager.getCycle().next()); i++) {
            if (TurnManager.getCycle().currentPlayer().equals(Cycle.BREAK_NAME)) {
                announceTurn();
                return;
            }

            if (i >= TurnManager.getCycle().size()) {
                Bukkit.broadcastMessage("No player in the cycle is currently present!");
                return;
            }
        }

        announceTurn();
        startTimer();
        notifyTurnIncrement();
    }

    /**
     * Helper method to check whether player is currently available A player is
     * available if the player is online
     *
     * @param input name of the player to be checked
     * @return whether player specified is available
     */
    private boolean checkPlayerAvailability(String input) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String currPlayer = player.getName();
            if (input.equalsIgnoreCase(currPlayer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerTurnSubscriber(TurnSubscriber sub){
        subscriberList.add(sub);
    }

    @Override
    public boolean removeTurnSubscriber(TurnSubscriber sub){
        return subscriberList.remove(sub);
    }

    @Override
    public void notifyTurnIncrement(){
        subscriberList.forEach(TurnSubscriber::updateTurnIncrement);
    }

    /**
     * Starts the timer with the default value of 15 minutes.
     */
    public void startTimer() {
        startTimer(DEFAULT_TIMER_MINS);
    }

    /**
     * Starts the timer for the specified minute
     *
     * @param minute minutes to be counted
     */
    public void startTimer(int minute) {
        if (timer != null) {
            timer.haltTimer();
        }

        timer = new TurnTimer(minute);
        timer.start();
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        if (timer != null) {
            timer.haltTimer();
            timer = null;
        }
    }

    /**
     * Pauses the current timer, with up to TIMER_RESOLUTION_MINS loss of time
     */
    public void pauseTimer() {
        timer.pauseTimer();
    }

    /**
     * Resumes the timer, with up to TIMER_RESOLUTION_MINS delay in starting
     */
    public void resumeTimer() {
        timer.resumeTimer();
    }
}