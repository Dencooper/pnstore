package com.dencooper.pnstore.domain.dto;

import com.dencooper.pnstore.utils.RegisterChecker;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@RegisterChecker
@Getter
@Setter
public class RegisterDTO {

    @Size(min = 2, message = "Họ phải có tối thiểu 2 ký tự")
    private String firstName;

    @Size(min = 2, message = "Tên phải có tối thiểu 2 ký tự")
    private String lastName;

    @NotNull
    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Size(min = 6, message = "Mật kh phải có tối thiểu 6 ký tự")
    private String password;

    private String confirmPassword;
}
