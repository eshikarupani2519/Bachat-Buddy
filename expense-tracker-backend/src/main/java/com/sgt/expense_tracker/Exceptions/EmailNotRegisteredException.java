package com.sgt.expense_tracker.Exceptions;

public class EmailNotRegisteredException extends Exception {
    public EmailNotRegisteredException() {
        super("Email is not registered");
    }
}
