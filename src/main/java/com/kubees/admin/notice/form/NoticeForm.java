package com.kubees.admin.notice.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class NoticeForm {

    private Long id;

    @NotBlank
    @Length(min = 5, max=100)
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String publishTime;

    private String publishDate;

    private String publishHour;

    private String publishMinutes;

    private String createdId;

}
