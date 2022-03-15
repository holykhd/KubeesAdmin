package com.kubees.admin.login.form;

import com.kubees.admin.login.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginFormValidator implements Validator {

    private final LoginRepository loginRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginForm.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        LoginForm loginForm = (LoginForm) object;
        if (loginRepository.existsByEmail(loginForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{loginForm.getEmail()}, "이메일이 존재하지 않습니다.");
        }
    }
}
