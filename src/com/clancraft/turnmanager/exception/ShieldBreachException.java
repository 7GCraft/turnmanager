package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class ShieldBreachException extends Exception {
    public ShieldBreachException() {
        super();
    }
    
    public ShieldBreachException(String message) {
        super(message);
    }
}