package com.example.TicketBooking.exception;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends AppException{
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
