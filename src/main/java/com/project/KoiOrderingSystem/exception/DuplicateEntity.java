package com.project.KoiOrderingSystem.exception;

public class DuplicateEntity extends  RuntimeException{
    public DuplicateEntity(String message) {
        super(message);
    }
}
