package com.kubees.admin.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/room/")
@RequiredArgsConstructor
public class RoomController {

    /**
     * 룸 목록 조회
     */
    @GetMapping("/list")
    public String userList() {
        return "room/list";
    }

    /**
     * 룸 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
        return "room/detail";
    }

}
