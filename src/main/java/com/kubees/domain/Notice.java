package com.kubees.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attachOid;             // 첨부파일 고유번호

    private String title;               // 제목

    @Lob
    private String contents;             // 내용

    private String publishTime;         // 발행시간(현재 : now, 예약 : reservation)

    private String publishDate;         // 발행시간(날짜 선택)

    private String publishHour;         // 발행시간(시간)

    private String publishMinutes;       // 발행시간(분)
    private int hit;                    // 조회수
    private String openFlag;            // 화면 출력 상태(출력 : Y / 숨김 : N)

    private LocalDateTime createdAt;     // 등록일

    private String createdId;            // 등록자 아이디

    private LocalDateTime updatedAt;     // 수정일

    private String updatedId;            // 수정자 아이디

    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
        this.hit = 0;
        this.openFlag = "Y";
    }
}
