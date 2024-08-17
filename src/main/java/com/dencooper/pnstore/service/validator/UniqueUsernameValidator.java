package com.dencooper.pnstore.service.validator;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.service.UserService;
import com.dencooper.pnstore.utils.UniqueUsernameChecker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameChecker, String> {

    private final UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean valid = true;

        if (this.userService.checkExistEmail(email)) {
            valid = false;
        }
        return valid;
    }
}
