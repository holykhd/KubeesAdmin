package com.kubees.admin.Scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
//@Component
public class NoticeSchedulerController {

//    "0 0 * * * *" = the top of every hour of every day.
//    "*/10 * * * * *" = 매 10초마다 실행한다.
//    "0 0 8-10 * * *" = 매일 8, 9, 10시에 실행한다
//    "0 0 6,19 * * *" = 매일 오전 6시, 오후 7시에 실행한다.
//    "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
//    "0 0 9-17 * * MON-FRI" = 오전 9시부터 오후 5시까지 주중(월~금)에 실행한다.
//    "0 0 0 25 12 ?" = every Christmas Day at midnight

    private final String second = "*/1";
    private final String second2 = "*/2";
//    @Scheduled(cron = "*/1 * * * * *")
//    @Scheduled(cron = second + " * * * * *")
    public void printDate() {
        log.info("now Date ={}", LocalDateTime.now());
    }

//    @Scheduled(cron = second2 + " * * * * *")
    public void printDate2() {
        log.info("now Date2 ={}", LocalDateTime.now());
    }

    // 매일 5시 30분 0초에 실행한다.
    @Scheduled(cron = "0 30 5 * * *")
    public void aJob() {

        // 실행될 로직
    }

    // 매월 1일 0시 0분 0초에 실행한다.
    @Scheduled(cron = "0 0 0 1 * *")
    public void anotherJob() {

        // 실행될 로직
    }

    // 애플리케이션 시작 후 60초 후에 첫 실행, 그 후 매 60초마다 주기적으로 실행한다.
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void otherJob() {

        // 실행될 로직
    }


    // 애플리케이션 시작 후 60초 후에 첫 실행, 그 후 매 1초마다 주기적으로 실행한다.
    @Scheduled(fixedDelay = 1000)
    public void otherJob2() throws ParseException {
        log.info("이거 샐행 될까요???");

//        String date = "2012-02-08 11:13:42,570";
        String date = "2012-02-08 11:13:00,000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        Date date1 = simpleDateFormat.parse(date);
        log.info("simpleDateFormat ={}", simpleDateFormat);
        log.info("date1 ={}", date1);
        log.info("date1 ={}", date1.getTime());

        // 실행될 로직
    }
}
