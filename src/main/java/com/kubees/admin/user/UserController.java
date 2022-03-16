package com.kubees.admin.user;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.domain.Account;
import com.kubees.domain.QAccount;
import com.kubees.domain.UserStatus;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kubees.domain.QAccount.account;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final AccountRepository accountRepository;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    /**
     * 회원 목록
     */
    @GetMapping("/list")
    public String userList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
//        log.info("principal ={}", principalDetails.getAccount());
//
//        queryFactory = new JPAQueryFactory(em);
//        QAccount a = new QAccount("a");
//        Account account = queryFactory.select(a)
//                .from(a)
//                .where(a.email.eq("444@gmail.com"))
//                .fetchOne();
//
//        log.info("account ={}", account);
//
//        QAccount a2 = new QAccount("a2");
//        Account account2 = queryFactory.select(QAccount.account)
//                .from(a2)
//                .where(a2.email.eq("123@gmail.com"))
//                .fetchOne();
//        Account account1 = account2;
//
//        log.info("account2 ={}", account);

        List<Account> userList = accountRepository.findAll();






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
