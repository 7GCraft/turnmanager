package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class DuplicatePlayerException extends Exception {
    public DuplicatePlayerException() {
        super();
    }

    public DuplicatePlayerException(String message) {
        super(message);
    }
}