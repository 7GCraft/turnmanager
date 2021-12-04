package com.clancraft.turnmanager.turn;

import com.clancraft.turnmanager.TMConstants;
import com.clancraft.turnmanager.TurnManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * A class to create threads that function as a timer. The timer duration is
 * configurable, and will keep alerting users after time is over. Timer thread
 * automatically stops (by default) 60 minutes after time is over.
 */
public class TurnTimer {
    private static final long NORMAL_INTERVAL_MINS = 5;
    private static final long OVERTIME_INTERVAL_MINS = 1;

    private int normalBroadcastId = -1;
    private int overtimeBroadcastId = -1;
    private int overtimeTransitionId = -1;

    private long deadlineTimeMillis;
    private long timeRemainingMillis;

    /**
     * Default constructor that accepts the duration of the timer.
     *
     * @param minute duration of the timer
     */
    public TurnTimer(int minute) {
        timeRemainingMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minute);
        startTimer(TimeUnit.MINUTES.toMillis(minute));
    }

    /**
     * Starts a timer that ends at current time + timerDurationMillis.
     * Timer runs a broadcast action that happens every NORMAL_INTERVAL_MINS
     * before the deadline, every OVERTIME_INTERVAL_MINS after the deadline.
     *
     * @param timerDurationMillis how far in the future deadline is
     */
    private void startTimer(long timerDurationMillis) {
        Plugin plugin = TurnManager.getPlugin();
        deadlineTimeMillis = System.currentTimeMillis() + timerDurationMillis;

        long initMinsRemaining = TimeUnit.MILLISECONDS.toMinutes(deadlineTimeMillis - System.currentTimeMillis());
        Bukkit.broadcastMessage(TMConstants.TIMER_INITIAL);
        Bukkit.broadcastMessage(String.format(TMConstants.TIMER_COUNTDOWN, initMinsRemaining));

        // refactored because repeated in timerDurationMillis > 0 and timerDurationMillis <= 0
        Runnable scheduleOvertimeBroadcast = () -> {
            overtimeBroadcastId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                    () -> {
                        long minsOvertime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - deadlineTimeMillis);
                        Bukkit.broadcastMessage(String.format(TMConstants.TIMER_OVERTIME, minsOvertime));
                    },
                    0,
                    OVERTIME_INTERVAL_MINS * TMConstants.TICKS_IN_MINUTE
            );
        };

        // TODO check if the modulo math is correct.
        if (timerDurationMillis > 0) {
            normalBroadcastId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                    () -> {
                        long minsRemaining = TimeUnit.MILLISECONDS.toMinutes(deadlineTimeMillis - System.currentTimeMillis());
                        Bukkit.broadcastMessage(String.format(TMConstants.TIMER_COUNTDOWN, minsRemaining));
                    },
                    timerDurationMillis % TimeUnit.MINUTES.toMillis(NORMAL_INTERVAL_MINS),
                    NORMAL_INTERVAL_MINS * TMConstants.TICKS_IN_MINUTE
            );

            Runnable transition = () -> {
                Bukkit.broadcastMessage(String.format(TMConstants.TIMER_TIMEUP, TimeUnit.MILLISECONDS.toMinutes(deadlineTimeMillis - System.currentTimeMillis())));
                if (normalBroadcastId != -1) {
                    Bukkit.getScheduler().cancelTask(normalBroadcastId);
                    normalBroadcastId = -1;
                }

                scheduleOvertimeBroadcast.run();
            };

            overtimeTransitionId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, transition, timerDurationMillis * TMConstants.TICKS_IN_MINUTE);
        } else {
            scheduleOvertimeBroadcast.run();
        }
    }

    /**
     * Method to stop the execution of the thread.
     */
    public void haltTimer() {
        if (normalBroadcastId != -1) {
            Bukkit.getScheduler().cancelTask(normalBroadcastId);
            normalBroadcastId = -1;
        }

        if (overtimeBroadcastId != -1) {
            Bukkit.getScheduler().cancelTask(overtimeBroadcastId);
            overtimeBroadcastId = -1;
        }

        if (overtimeTransitionId != -1) {
            Bukkit.getScheduler().cancelTask(overtimeTransitionId);
            overtimeTransitionId = -1;
        }
    }

    /**
     * Pauses the current timer, with up to TIMER_RESOLUTION_MINS loss of time
     */
    public void pauseTimer() {
        haltTimer();
        timeRemainingMillis = deadlineTimeMillis - System.currentTimeMillis();
        Bukkit.broadcastMessage(TMConstants.TIMER_PAUSE);
    }

    /**
     * Resumes the timer, with up to TIMER_RESOLUTION_MINS delay in starting
     */
    public void resumeTimer() {
        startTimer(timeRemainingMillis);
    }
}