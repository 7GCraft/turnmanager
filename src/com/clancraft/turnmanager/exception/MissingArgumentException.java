package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class MissingArgumentException extends Exception {
    public MissingArgumentException() {
        super();
    }
    
    public MissingArgumentException(String message) {
        super(message);
    }
}