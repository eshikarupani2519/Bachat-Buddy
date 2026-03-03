package com.sgt.expense_tracker.Exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException() {
        super("Invalid email format");
    }
}
