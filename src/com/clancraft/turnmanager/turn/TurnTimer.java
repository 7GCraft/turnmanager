package com.clancraft.turnmanager.turn;

import org.bukkit.Bukkit;

import com.clancraft.turnmanager.*;

/**
 * A class to create threads that function as a timer. The timer duration is
 * configurable, and will keep alerting users after time is over. Timer thread
 * automatically stops (by default) 60 minutes after time is over.
 */
public class TurnTimer extends Thread {
    private final int MAX_OVERTIME_MINS = 60;
    private final int NORMAL_INTERVAL = 5;
    private final int OVERTIME_INTERVAL = 1;
    private final int TIMER_RESOLUTION_MINS = 1;
    private final int MIN_TO_MS = 60000;

    private int minutesRemaining;
    private volatile boolean terminateNow = false;
    private volatile boolean isPaused = false;

    /**
     * Default constructor that accepts the duration of the timer.
     *
     * @param minute duration of the timer
     */
    public TurnTimer(int minute) {
        minutesRemaining = minute;
    }

    /**
     * Executed when the thread is running. Keeps track of time remaining by
     * decrementing variable value every NORMAL_INTERVAL minutes. Timer keeps
     * running after time is up, reminding user every OVERTIME_INTERVAL, up to
     * MAX_OVERTIME_MINS minutes.
     *
     * @see com.clancraft.turnmanager.TMConstants#NORMAL_INTERVAL NORMAL_INTERVAL
     * @see com.clancraft.turnmanager.TMConstants#OVERTIME_INTERVAL
     *      OVERTIME_INTERVAL
     * @see com.clancraft.turnmanager.TMConstants#MAX_OVERTIME_MINS
     *      MAX_OVERTIME_MINS
     */
    @Override
    public void run() {
        Bukkit.broadcastMessage(String.format(TMConstants.TIMER_INITIAL, minutesRemaining));
        while (!terminateNow) {
            if (minutesRemaining > 0) {
                if (minutesRemaining % NORMAL_INTERVAL == 0) {
                    Bukkit.broadcastMessage(String.format(TMConstants.TIMER_COUNTDOWN, minutesRemaining));
                }
            } else {
                Bukkit.broadcastMessage(String.format(TMConstants.TIMER_TIMEUP, minutesRemaining));
                break;
            }

            try {
                Thread.sleep(TIMER_RESOLUTION_MINS * MIN_TO_MS);
                minutesRemaining -= TIMER_RESOLUTION_MINS;

            } catch (Exception InterruptedException) {
                TurnManager.getPlugin().getLogger().warning("Turn timer sleep has been interrupted!");
            }

            if (isPaused) {
                trap();
            }
        }

        while (!terminateNow && minutesRemaining < MAX_OVERTIME_MINS) {
            if (minutesRemaining % OVERTIME_INTERVAL == 0) {
                Bukkit.broadcastMessage(String.format(TMConstants.TIMER_OVERTIME, minutesRemaining));
            }
            try {
                Thread.sleep(TIMER_RESOLUTION_MINS * MIN_TO_MS);
            } catch (Exception InterruptedException) {
                TurnManager.getPlugin().getLogger().warning("Turn timer sleep has been interrupted!");
            }
            minutesRemaining += TIMER_RESOLUTION_MINS;
        }
    }

    /**
     * Method to stop the execution of the thread.
     */
    public void haltTimer() {
        terminateNow = true;
    }

    /**
     * Pauses the current timer, with up to TIMER_RESOLUTION_MINS loss of time
     */
    public void pauseTimer() {
        isPaused = true;
    }

    /**
     * Resumes the timer, with up to TIMER_RESOLUTION_MINS delay in starting
     */
    public void resumeTimer() {
        isPaused = false;
    }

    /**
     * Helper method to trap computation during timer pause
     */
    private void trap() {
        while (isPaused) {
            try {
                Thread.sleep(TIMER_RESOLUTION_MINS * MIN_TO_MS);
            } catch (InterruptedException e) {
                TurnManager.getPlugin().getLogger().warning("Turn timer sleep has been interrupted!");
            }
        }
    }
}