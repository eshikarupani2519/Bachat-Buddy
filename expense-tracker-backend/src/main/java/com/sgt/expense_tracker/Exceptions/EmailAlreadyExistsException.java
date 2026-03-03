package com.sgt.expense_tracker.Exceptions;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException() {
        super("Email already exists!");
    }
}
