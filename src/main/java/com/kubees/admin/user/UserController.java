package com.kubees.admin.user;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.user.form.SearchForm;
import com.kubees.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final AccountRepository accountRepository;
    private final UserService userService;

    /**
     * 회원 목록
     */
    @GetMapping("/list")
    public String userList(Model model, SearchForm searchForm, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        model.addAttribute("searchForm", searchForm);
        Page<Account> userList = userService.searchUserList(searchForm, pageable);
        model.addAttribute("userList", userList);
        return "user/list";
    }

    /**
     * 회원 상세
     */
    @GetMapping("/detail/{email}")
    public String userDetail(@PathVariable String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        model.addAttribute("account", account);
        return "user/detail";
    }
}
