package com.kubees.admin.api;

import com.kubees.admin.notice.NoticeService;
import com.kubees.admin.user.UserService;
import com.kubees.domain.Feed;
import com.kubees.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoticeFlagChangeController {

    private final NoticeService noticeService;
    private final UserService userService;

    /**
     * 공지사항 출력 상태
     */
    @GetMapping("/notice/openFlag")
    public Map<String, Object> noticeModal(@RequestParam(required = false) Map<String, Object> param) {

        Long id = Long.parseLong(String.valueOf(param.get("id")));
        String openFlag = (String) param.get("openFlag");

        Map<String, Object> map = new HashMap<>();

        Notice notice = noticeService.getNoticeDetailProcessor(id);

        Notice noticeFlagChange = noticeService.changeNoticeFlagProcessor(notice, openFlag);

        // id 값을 가져와서 openFlag값을 변경해준다.
        // 변경한 값을 openFlag에 담아서 보내준다.
        map.put("id", noticeFlagChange.getId());
        map.put("openFlag", noticeFlagChange.getOpenFlag());
        return map;
    }

    /**
     * 회원 상세 피드 보이기/숨기기
     */
    @GetMapping("/user/detail/feedStatus")
    public Map<String, Object> feedStatus(@RequestParam(required = false) Map<String, Object> param) {
        Long id = Long.valueOf(String.valueOf(param.get("id")));

        boolean feedStatus = Boolean.parseBoolean((String) param.get("feedStatus"));

        Feed feed = userService.getFeedProcessor(id);

        Feed newFeed = userService.changeFeedStatusProcessor(feed, feedStatus);
        Map<String, Object> map = new HashMap<>();

        map.put("id", newFeed.getId());
        map.put("feedStatus", newFeed.isFeedStatus());
        return map;
    }
}