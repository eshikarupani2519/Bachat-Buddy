package com.sgt.expense_tracker.Exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid email format");
    }
}
