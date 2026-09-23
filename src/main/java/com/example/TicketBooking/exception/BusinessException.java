package com.example.TicketBooking.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends AppException{
    public BusinessException(String message) {
        super(message, "BUSINESS_ERROR", HttpStatus.BAD_REQUEST);
    }
}
