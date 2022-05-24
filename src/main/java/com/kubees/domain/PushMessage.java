package com.kubees.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class PushMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 푸시 메시지 고유 번호

    @Column(name = "title")
    private String title;                   // 제목

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;                 // 메시지 내용내용

    @Column(name = "publish_time")
    private String publishTime;         // 발행시간(현재 : now, 예약 : reservation)

    @Column(name = "publish_date")
    private String publishDate;         // 발행시간(날짜 선택)

    @Column(name = "publish_hour")
    private String publishHour;         // 발행시간(시간)

    @Column(name = "publish_minutes")
    private String publishMinutes;       // 발행시간(분)


    @Column(name = "hit")
    private int hit;                        // 조회 수

    @Column(name = "created_at")
    private LocalDateTime createdAt;        // 등록일

    @Column(name = "open_flag")
    private String openFlag;            // 화면 출력 상태(출력 : Y / 숨김 : N)

    @Column(name = "open_date")
    private String openDate;            // 글 오픈 시간

    @Column(name = "created_id")
    private String createdId;               // 등록자 아이디

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;        // 수정일

    @Column(name = "updated_id")
    private String updatedId;               // 수정자 아이디

    @Column(name = "btn_name_first")
    private String btnNameFirst;            // 첫번째 번튼 명

    @Column(name = "btn_link_first")
    private String btnLinkFirst;            // 첫번째 번튼 링크

    @Column(name = "btn_use_first")
    private String btnUseFirst;             // 첫번째 번튼 사용여부(use : 사용, stop : 사용중지)

    @Column(name = "btn_name_second")
    private String btnNameSecond;            // 두번째 번튼 명

    @Column(name = "btn_link_second")
    private String btnLinkSecond;            // 두번째 번튼 링크

    @Column(name = "btn_use_second")
    private String btnUseSecond;             // 두번째 번튼 사용여부(use : 사용, stop : 사용중지)

    @Column(name = "btn_name_third")
    private String btnNameThird;             // 세번째 번튼 명

    @Column(name = "btn_link_third")
    private String btnLinkThird;             // 세번째 번튼 링크

    @Column(name = "btn_use_third")
    private String btnUseThird;             // 세번째 번튼 사용여부(use : 사용, stop : 사용중지)

    @PrePersist
    private void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }

}
