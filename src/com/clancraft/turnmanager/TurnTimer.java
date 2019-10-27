package com.clancraft.turnmanager;

import org.bukkit.Bukkit;

public class TurnTimer extends Thread {
    private final int NORMAL_INTERVAL = 5;
    private final int OVERTIME_INTERVAL = 1;
    private final int MIN_TO_MS = 60000;

    private int minutesRemaining;
    private volatile boolean terminateNow = false;

    public TurnTimer(int minute) {
        minutesRemaining = minute;
    }

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
                System.err.println("My slumber is disturbed.");
            }
        }

        while (!terminateNow && minutesRemaining < 60) {
            Bukkit.broadcastMessage(String.format(TMStrings.TIMER_OVERTIME, minutesRemaining));
            try {
                Thread.sleep(OVERTIME_INTERVAL * MIN_TO_MS);
            } catch (Exception InterruptedException) {
                System.err.println("My rest is disturbed.");
            }
            minutesRemaining += OVERTIME_INTERVAL;
        }
    }

    public void halt() {
        terminateNow = true;
    }
}