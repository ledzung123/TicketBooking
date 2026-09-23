package com.example.TicketBooking.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AppException{
    public DuplicateResourceException(String message) {
        super(message, "DUPLICATE_RESOURCE", HttpStatus.CONFLICT);
    }
}
