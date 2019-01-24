package com.example.semen.contactslist.repo;

public class NotFoundContactException extends Exception {
    public NotFoundContactException(String message) {
        super(message);
    }
}
