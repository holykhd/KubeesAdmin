package com.kubees.admin.qna.form;

import com.kubees.domain.enumType.QnATypeStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class QnAForm {

    @Email
    private String email;                   // 등록자 이메일

    @NotBlank
    @Length(min = 6, max = 100)
    private String title;                   // 제목

    @NotBlank
    @Length(min = 10, max = 255)
    private String question;                // 문의내용

    @NotBlank
    @Length(min = 10, max = 255)
    private String answer;            // 답변

    @NotBlank
    private QnATypeStatus type;             // 문의유형

    @NotBlank
    private String createdId;                // 등록자 아이디

    private String answerId;                // 답변자 아이디

    private String answerStatus;

    private LocalDateTime answerCreatedAt;        // 답변 등록일

}
