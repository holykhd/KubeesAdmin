package com.kubees.admin.login;

import com.kubees.admin.login.form.LoginForm;
import com.kubees.admin.login.form.LoginFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginFormValidator loginFormValidator;

    @InitBinder("loginForm")
    private void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginFormValidator);
    }

    /**
     * 로그인
     */
    @GetMapping({"/", "/login"})
    public String loginForm(LoginForm loginForm, Model model) {
        model.addAttribute(new LoginForm());
        return "login";
    }
}
