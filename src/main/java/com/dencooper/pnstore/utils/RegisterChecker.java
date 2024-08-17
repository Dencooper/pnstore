package com.dencooper.pnstore.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dencooper.pnstore.service.validator.RegisterValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegisterValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterChecker {

    String message() default "Đăng ký tài khoản không thành công";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
