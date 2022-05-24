package com.kubees.admin.user.form;

import lombok.Data;

@Data
public class FeedForm {
    private Long id;
    private Long attachFeedOid;
    private String contents;
    private boolean feedStatus;
    private String imagePath;
    private String bgColor;
}
