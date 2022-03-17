package com.kubees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attachOid;             // 첨부파일 고유번호

    private String title;               // 제목
    private String question;            // 문의내용
    private String answer;              // 답변
    private String type;                // 문의유형
    private int hit;                    // 조회수
    private LocalDateTime createAt;     // 등록일
    private String createId;            // 등록자 아이디
    private LocalDateTime updateAt;     // 수정일
    private String updateId;            // 수정자 아이디
}
