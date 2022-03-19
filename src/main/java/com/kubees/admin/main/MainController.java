package com.kubees.admin.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @RequestMapping("/admin/default")
    public String main(HttpServletRequest request) {
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
