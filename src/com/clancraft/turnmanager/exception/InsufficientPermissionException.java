package com.clancraft.turnmanager.exception;

@SuppressWarnings("serial")
public class InsufficientPermissionException extends Exception {
    public InsufficientPermissionException() {
        super();
    }
    
    public InsufficientPermissionException(String message) {
        super(message);
    }
}