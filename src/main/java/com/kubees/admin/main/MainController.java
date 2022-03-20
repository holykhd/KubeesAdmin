package com.kubees.admin.main;

import com.kubees.admin.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @RequestMapping("/admin/default")
    public String main(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("userRoleInfo ={}", principalDetails.getAccount().getRoles());

        String redirectURL = "";
        if (request.isUserInRole("ROLE_ADMIN")) {
            redirectURL = "redirect:/admin/user/list";
        } else if (request.isUserInRole("ROLE_MANAGER")) {
            redirectURL = "redirect:/admin/partners/list";
        } else {
            redirectURL = "redirect:/";
        }
        return redirectURL;
    }
}
