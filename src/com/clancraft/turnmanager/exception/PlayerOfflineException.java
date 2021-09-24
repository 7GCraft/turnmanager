package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class PlayerOfflineException extends Exception {
    public PlayerOfflineException() {
        super();
    }

    public PlayerOfflineException(String message) {
        super(message);
    }
}