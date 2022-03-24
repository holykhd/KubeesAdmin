package com.kubees.admin.pushMessage.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PushMessageForm {

    @NotBlank
    @Length(min = 4, max = 50)
    private String title;                   // 제목

    @NotBlank
    private String message;                 // 메시지 내용내용

    private String sendUser;                // 수신자

    private String sendCycle;               // 발송 주기

    private String sendTime;                // 발송 시간(현재 : now, 예약 : reservation)

    private String sendDate;                // 발송시간(날짜 선택)

    private String sendHour;                // 발송시간(시간)

    private String sendMinutes;             // 발송시간 (분)

    private LocalDateTime createdAt;        // 등록일

    private String createdId;               // 등록자 아이디

    private LocalDateTime updatedAt;        // 수정일

    private String updatedId;               // 수정자 아이디

    private String btnNameFirst;            // 첫번째 번튼 명

    private String btnLinkFirst;            // 첫번째 번튼 링크

    private String btnUseFirst;             // 첫번째 번튼 사용여부(use : 사용, stop : 사용중지)

    private String btnNameSecond;            // 두번째 번튼 명

    private String btnLinkSecond;            // 두번째 번튼 링크

    private String btnUseSecond;             // 두번째 번튼 사용여부(use : 사용, stop : 사용중지)

    private String btnNameThird;             // 첫번째 번튼 명

    private String btnLinkThird;             // 세번째 번튼 링크

    private String btnUseThird;             // 세번째 번튼 사용여부(use : 사용, stop : 사용중지)
}
