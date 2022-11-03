package com.binh.exercise2.error;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String field) {
        super("Invalid input: " + field);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
