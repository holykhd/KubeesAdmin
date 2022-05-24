package com.kubees.admin.api;

import com.kubees.admin.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/account")
public class AccountFlagChangeController {

    private final UserService userService;

    @PostMapping("/changeStatus/{id}/{userStatus}")
    private ResponseEntity changeAccountStatus(@PathVariable Long id, @PathVariable String userStatus) {
        userService.changeAccountStatus(id, userStatus);
        return null;
    }
}
