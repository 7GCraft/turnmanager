package com.clancraft.turnmanager;

import org.bukkit.Bukkit;

public class TurnTimer extends Thread {
    private final int FIVE_MINUTES = 300000;
    private final int ONE_MINUTE = 60000;

    private int minutesRemaining = 20;
    private volatile boolean terminateNow = false;

    public void halt() {
        terminateNow = true;
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
                Thread.sleep(FIVE_MINUTES);
            } catch (Exception InterruptedException) {
                System.err.println("My slumber is disturbed.");
            }

            minutesRemaining -= 5;
        }

        while (!terminateNow) {
            Bukkit.broadcastMessage(String.format(TMStrings.TIMER_OVERTIME, minutesRemaining));
            try {
                Thread.sleep(ONE_MINUTE);
            } catch (Exception InterruptedException) {
                System.err.println("My rest is disturbed.");
            }
            minutesRemaining += 1;
        }
    }
}