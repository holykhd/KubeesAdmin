package com.kubees.admin.login.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginForm {

    @NotBlank
    @Length(min = 4, max = 20)
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;
}
