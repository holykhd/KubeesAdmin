package com.kubees.admin.pushMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/pushMessage/")
@RequiredArgsConstructor
public class PushMessageController {

    /**
     * 푸시 메시지 목록 조회
     */
    @GetMapping("/list")
    public String userList() {
        return "pushMessage/list";
    }

    /**
     * 푸시 메시지 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
        return "pushMessage/detail";
    }

    /**
     * 푸시 메시지 수정 화면
     */
    @GetMapping("/edit")
    public String edit() {
        return "pushMessage/edit";
    }

}
