package com.clancraft.turnmanager;

import org.bukkit.Bukkit;

/**
 * A class to create threads that function as a timer. The timer duration is
 * configurable, and will keep alerting users after time is over. Timer thread 
 * automatically stops (by default) 60 minutes after time is over.
 */
public class TurnTimer extends Thread {
    private final int MAX_OVERTIME_MINS = 60;
    private final int NORMAL_INTERVAL = 5;
    private final int OVERTIME_INTERVAL = 1;
    private final int MIN_TO_MS = 60000;

    private int minutesRemaining;
    private volatile boolean terminateNow = false;

    /**
     * Default constructor that accepts the duration of the timer.
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
     */
    @Override
    public void run() {
        Bukkit.broadcastMessage(String.format(TMStrings.TIMER_INITIAL, minutesRemaining));
        while (!terminateNow) {
            if (minutesRemaining > 0) {
                Bukkit.broadcastMessage(String.format(TMStrings.TIMER_COUNTDOWN, minutesRemaining));
            } else {
                Bukkit.broadcastMessage(String.format(TMStrings.TIMER_TIMEUP, minutesRemaining));
                break;
            }

            try {
                if (minutesRemaining >= NORMAL_INTERVAL) {
                    Thread.sleep(NORMAL_INTERVAL * MIN_TO_MS);
                    minutesRemaining -= NORMAL_INTERVAL;
                } else {
                    Thread.sleep(minutesRemaining * MIN_TO_MS);
                    minutesRemaining -= minutesRemaining;
                }
                
            } catch (Exception InterruptedException) {
                // TODO print error message to logger.
            }
        }

        while (!terminateNow && minutesRemaining < MAX_OVERTIME_MINS) {
            Bukkit.broadcastMessage(String.format(TMStrings.TIMER_OVERTIME, minutesRemaining));
            try {
                Thread.sleep(OVERTIME_INTERVAL * MIN_TO_MS);
            } catch (Exception InterruptedException) {
                // TODO print error message to logger.
            }
            minutesRemaining += OVERTIME_INTERVAL;
        }
    }

    /**
     * Method to stop the execution of the thread.
     */
    public void halt() {
        terminateNow = true;
    }
}