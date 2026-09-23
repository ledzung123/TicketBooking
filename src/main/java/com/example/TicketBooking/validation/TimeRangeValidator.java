package com.example.TicketBooking.validation;

import com.example.TicketBooking.dto.ShowtimeCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, ShowtimeCreateDTO> {
    @Override
    public void initialize(ValidTimeRange constraintAnnotation) {

    }

    @Override
    public boolean isValid(ShowtimeCreateDTO showtimeCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (showtimeCreateDTO.getStartTime() == null || showtimeCreateDTO.getEndTime() == null) {
            return true;
        }

        return showtimeCreateDTO.getEndTime().isAfter(showtimeCreateDTO.getStartTime());
    }
}
