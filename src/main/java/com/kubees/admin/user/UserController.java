package com.kubees.admin.user;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.attach.AttachFeedService;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.user.form.FeedForm;
import com.kubees.admin.user.form.SearchForm;
import com.kubees.domain.Account;
import com.kubees.domain.AttachFeed;
import com.kubees.domain.Feed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String userList(Model model, SearchForm searchForm,
                           @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        model.addAttribute("searchForm", searchForm);
        Page<Account> userList = userService.searchUserList(searchForm, pageable);
        model.addAttribute("header", "userList");
        model.addAttribute("userList", userList);
        return "user/list";
    }

    /**
     * 사용 제한 회원 목록
     */
    @GetMapping("/deprecatedList")
    public String userDeprecatedList(Model model, SearchForm searchForm,
                                     @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("searchForm", searchForm);
        Page<Account> userList = userService.searchUserDeprecatedList(searchForm, pageable);
        model.addAttribute("header", "deprecated");
        model.addAttribute("userList", userList);
        return "user/list";
    }

    /**
     * 차단중인 회원 목록
     */
    @GetMapping("/blockList")
    public String userBlockList(Model model, SearchForm searchForm,
                                @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("searchForm", searchForm);
        Page<Account> userList = userService.searchUserBlockList(searchForm, pageable);
        model.addAttribute("userList", userList);
        model.addAttribute("header", "blockList");
        return "user/blockList";
    }


    /**
     * 회원 상세
     */
    @GetMapping("/detail/{email}")
    public String userDetail(@PathVariable String email, Model model, SearchForm searchForm) {
        boolean feedStatus = searchForm.isFeedStatus();
        log.info("booleanFeedStatus ={}", searchForm);

        Account account = accountRepository.findByEmail(email);
        List<FeedForm> feedList = userService.findByUserFeedListProcessor(email, feedStatus);
        int feedCount = userService.getFeedCount(email);

        model.addAttribute("feedList", feedList);
        model.addAttribute("feedStatus", feedStatus);

        model.addAttribute("account", account);
        model.addAttribute("feedCount", feedCount);
        return "user/detail";
    }

    @GetMapping("/detail/feedStatus/{email}/{feedStatus}")
    public String feedStatus(@PathVariable String email, @PathVariable String feedStatus, Model model) {
        boolean status = Boolean.parseBoolean(feedStatus);
        List<Feed> feedList = userService.getFeedStatusProcessor(email, status);

        log.info("feedList ={}", feedList);
        return "user/detail";
    }
}
