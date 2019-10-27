package com.clancraft.turnmanager;

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
        System.out.println(String.format(TMStrings.TIMER_INITIAL, minutesRemaining));
        while(!terminateNow) {
            try {
                Thread.sleep(FIVE_MINUTES);
            } catch (Exception InterruptedException) {
                System.err.println("My slumber is disturbed.");
            }
            
            minutesRemaining -= 5;
            if (minutesRemaining > 0) {
                System.out.println(String.format(TMStrings.TIMER_COUNTDOWN, minutesRemaining));
            } else {
                System.out.println(String.format(TMStrings.TIMER_TIMEUP, minutesRemaining));
                break;                
            }
        }

        while (!terminateNow) {
            try {
                Thread.sleep(ONE_MINUTE);
            } catch (Exception InterruptedException) {
                System.err.println("My rest is disturbed.");
            }
            minutesRemaining += 1;
            System.out.println(String.format(TMStrings.TIMER_OVERTIME, minutesRemaining));
        }
    }
}