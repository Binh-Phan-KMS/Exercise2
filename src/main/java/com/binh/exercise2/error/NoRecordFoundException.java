package com.binh.exercise2.error;

public class NoRecordFoundException extends RuntimeException{
    public NoRecordFoundException() {
        super("No record found");
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
