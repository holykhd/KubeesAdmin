package com.kubees.admin.qna;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/qna/")
@RequiredArgsConstructor
public class QnaController {

    /**
     * QnA 목록 조회
     */
    @GetMapping("/list")
    public String userList() {
        return "qna/list";
    }

    /**
     * QnA 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
        return "qna/detail";
    }

    /**
     * QnA 수정 화면
     */
    @GetMapping("/edit")
    public String edit() {
        return "qna/edit";
    }

}
