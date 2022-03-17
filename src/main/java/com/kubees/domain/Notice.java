package com.kubees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attachOid;             // 첨부파일 고유번호

    private String title;               // 제목
    private String content;             // 내용
    private String publishTime;         // 발행시간(현재, 예약)
    private String publishDate;         // 발행시간(날짜 선택)
    private String publishHour;         // 발행시간(시간)
    private String publishMinute;       // 발행시간(분)
    private int hit;                    // 조회수
    private String openFlag;            // 화면 출력 상태(출력 : Y / 숨김 : N)

    private LocalDateTime createAt;     // 등록일
    private String createId;            // 등록자 아이디

    private LocalDateTime updateAt;     // 수정일
    private String updateId;            // 수정자 아이디
}
