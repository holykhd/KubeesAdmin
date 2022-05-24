package com.kubees.admin.sns;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.sns.form.SearchForm;
import com.kubees.admin.sns.form.SnsDetailForm;
import com.kubees.admin.sns.form.SnsList;
import com.kubees.admin.user.UserService;
import com.kubees.admin.user.form.BlockForm;
import com.kubees.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/admin/sns/")
@RequiredArgsConstructor
public class SnsController {

    private final SnsService snsService;
    private final UserService userService;

    /**
     * SNS 목록 조회
     */
    @GetMapping("/list")
    public String snsList(Model model, SearchForm searchForm,
                           @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("searchForm", searchForm);
        Page<SnsList> snsList = snsService.getSnsListProcessor(searchForm, pageable);
        model.addAttribute("list", snsList);
        return "sns/list";
    }

    /**
     * SNS 상세 화면 조회
     */
    @GetMapping("/detail/{id}")
    public String snsDetail(@PathVariable Long id, Model model) {
        List<SnsDetailForm> snsDetail = snsService.getUserDetailProcessor(id);
        model.addAttribute("detail", snsDetail);
        Account userInfo = snsService.getUserInfo(id);
        model.addAttribute("userInfo", userInfo);

        List<BlockForm> blockToUserList = userService.getBlockToUserList(id);
        model.addAttribute("blockToUserList", blockToUserList);
        return "sns/detail";
    }


}
