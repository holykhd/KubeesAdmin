package com.kubees.admin.notice;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.domain.Account;
import com.kubees.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/notice/")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 목록 : list
     * 상세 : detail
     * 등록화면 : form
     * 등록 : form
     * 수정 : edit
     */


    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/list")
    public String userList(Model model) {
        List<Notice> noticeList = noticeService.getNoticeProcessor();
        model.addAttribute("list", noticeList);
        return "notice/list";
    }

    /**
     * 공지사항 등록 화면
     */
    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "notice/form";
    }

    /**
     * 공지사항 등록
     */
    @PostMapping("/form")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid NoticeForm noticeForm, BindingResult bindingResult) {
        log.info("noticeForm ={}", noticeForm);
        if (bindingResult.hasErrors()) {
            log.info("hasErrors ={}", bindingResult.getAllErrors());
            return "notice/form";
        }

        noticeService.createNoticeProcessor(noticeForm, principalDetails);

        return "redirect:/admin/notice/list";
    }

    /**
     * 공지사항 상세 화면 조회
     */
    @GetMapping("/detail/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        log.info("detail id ={}", id);
        Notice noticeDetail = noticeService.getNoticeDetailProcessor(id);
        log.info("noticeDetail ={}", noticeDetail);
        model.addAttribute("noticeDetail", noticeDetail);

        return "notice/detail";
    }

    /**
     * 공지사항 등록 화면
     */
    @GetMapping("/edit")
    public String userEdit() {
        return "notice/edit";
    }


}
