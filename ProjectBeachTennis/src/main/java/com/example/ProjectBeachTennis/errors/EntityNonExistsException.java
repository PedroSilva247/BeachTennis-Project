package com.example.ProjectBeachTennis.errors;

public class EntityNonExistsException extends RuntimeException{
    public EntityNonExistsException(String message) {
        super(message);
    }
}
