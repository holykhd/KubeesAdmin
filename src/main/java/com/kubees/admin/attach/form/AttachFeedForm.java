package com.kubees.admin.attach.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachFeedForm {
    private Long attachFeedOid;
    private String originalFileName;
    private String savedFileName;
    private String savedPath;
    private String thumbFileName;
}
