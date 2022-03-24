package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PushMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 푸시 메시지 고유 번호

    @Column(name = "title")
    private String title;                   // 제목

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;                 // 메시지 내용내용

    @Column(name = "send_user")
    private String sendUser;                // 수신자

    @Column(name = "send_cycle")
    private String sendCycle;               // 발송 주기

    @Column(name = "send_time")
    private String sendTime;                // 발송 시간(현재 : now, 예약 : reservation)

    @Column(name = "send_date")
    private String sendDate;                // 발송시간(날짜 선택)

    @Column(name = "send_hour")
    private String sendHour;                // 발송시간(시간)

    @Column(name = "send_minutes")
    private String sendMinutes;             // 발송시간 (분)

    @Column(name = "hit")
    private int hit;                        // 조회 수

    @Column(name = "created_at")
    private LocalDateTime createdAt;        // 등록일

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
