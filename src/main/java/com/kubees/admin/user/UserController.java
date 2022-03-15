package com.kubees.admin.user;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final AccountRepository accountRepository;

    @GetMapping("/list")
    public String userList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        log.info("principal ={}", principalDetails.getAccount());

        List<Account> account = accountRepository.findAll();
        model.addAttribute("account", account);
        return "user/list";
    }
}
