package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class DateSyncException extends Exception {
    public DateSyncException() {
        super();
    }

    public DateSyncException(String message) {
        super(message);
    }
}