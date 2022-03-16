package com.kubees.admin.sns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/sns/")
@RequiredArgsConstructor
public class SnsController {

    /**
     * SNS 목록 조회
     */
    @GetMapping("/list")
    public String userList() {
        return "sns/list";
    }

    /**
     * SNS 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
        return "sns/detail";
    }

}
