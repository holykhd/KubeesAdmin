package com.kubees.admin.attach.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttachImg {
    private Long attachImgId;
    private String attachImgSource;
    private String attachImgFile;
    private String attachImgPath;
    private String attachImgExt;
    private String attachImgSize;
    private String attachImgCreUsrId;
    private LocalDateTime attachImgCreDt;
}