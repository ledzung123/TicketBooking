package com.example.TicketBooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 1. Chỉ định class nào sẽ chứa logic kiểm tra (TimeRangeValidator ở bước 2)
@Target(ElementType.TYPE)
// 2. Đặt annotation này ở đâu? Ở cấp độ CLASS (vì cần nhìn cả 2 field startTime và endTime)
@Constraint(validatedBy = TimeRangeValidator.class)
// 3. Chạy lúc Runtime (khi app đang chạy, Spring mới đọc được)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeRange {
    String message() default "End time must be after start time";

    // Code mau bat buoc
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
