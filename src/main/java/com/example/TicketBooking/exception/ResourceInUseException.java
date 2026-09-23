package com.example.TicketBooking.exception;

import org.springframework.http.HttpStatus;

public class ResourceInUseException extends AppException{
    public ResourceInUseException(String message) {
        super(message, "RESOURCE_IN_USE", HttpStatus.CONFLICT);
    }
}
