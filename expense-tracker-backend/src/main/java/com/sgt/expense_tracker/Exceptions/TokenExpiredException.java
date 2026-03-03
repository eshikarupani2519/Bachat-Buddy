package com.sgt.expense_tracker.Exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Password reset link expired!");
    }
}
