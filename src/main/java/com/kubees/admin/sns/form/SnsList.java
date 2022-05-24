package com.kubees.admin.sns.form;

import lombok.Data;

@Data
public class SnsList {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private Long feedCount;
    private Long followCount;
}
