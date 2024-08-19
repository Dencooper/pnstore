package com.dencooper.pnstore.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.UserService;
import com.dencooper.pnstore.utils.UniqueUsernameChecker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameChecker, User> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        boolean valid = true;

        if (userService == null) {
            return true;
        }
        if (userService.checkExistEmail(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
