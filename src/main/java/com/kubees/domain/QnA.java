package com.kubees.domain;

import com.kubees.domain.enumType.QnAAnswerStatus;
import com.kubees.domain.enumType.QnATypeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;                   // 등록자 이메일

    private String nickname;                // 등록자 닉네임

    private String title;                   // 제목

    private String question;                // 문의내용


    private String answer;                  // 답변

    @Enumerated(STRING)
    private QnAAnswerStatus answerStatus;   // 답변상태

    @Enumerated(STRING)
    private QnATypeStatus type;             // 문의유형

    private int hit;                        // 조회수

    private LocalDateTime createdAt;         // 등록일

    private String createdId;                // 등록자 아이디

    private LocalDateTime updatedAt;         // 수정일

    private String updateId;                // 수정자 아이디

    private String answerId;                // 답변자 아이디

    private LocalDateTime answerCreatedAt;        // 답변 등록일

    private String answerUpdatedId;          // 수정자 아이디

    private LocalDateTime answerUpdatedAt;  // 수정일

    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
        this.hit = 0;
        this.answerStatus = QnAAnswerStatus.WAITING;
    }

}
