package com.kubees.admin.Scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PushMessageSchedulerController {
    private final PushMessageScheduler pushMessageScheduler;
    @GetMapping("/admin/setCron")
    public String setCron(){
        pushMessageScheduler.setCron("0/10 * * * * ?");
        return "success";
    }
}
