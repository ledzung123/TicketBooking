package com.example.TicketBooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Validate the start and end times together at class level.
@Target(ElementType.TYPE)
@Constraint(validatedBy = TimeRangeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeRange {
    String message() default "End time must be after start time";

    // Required by the Jakarta Validation annotation contract.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
