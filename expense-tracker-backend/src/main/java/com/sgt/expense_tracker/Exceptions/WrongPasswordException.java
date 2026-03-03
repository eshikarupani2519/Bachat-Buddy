package com.sgt.expense_tracker.Exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Invalid email or password");
    }
}
