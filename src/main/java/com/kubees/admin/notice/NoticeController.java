package com.kubees.admin.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/notice/")
@RequiredArgsConstructor
public class NoticeController {

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/list")
    public String userList() {
        return "notice/list";
    }

    /**
     * 공지사항 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
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
