package com.kubees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;              // 사용자 아이디

    private String showStatus;          // 숨김처리 여부

    private String title;               // 큐브 제목

    private String description;         // 큐브 설명

    private LocalDateTime createAt;     // 큐브 등록일

    private String attachId;            // 큐브 이미지 아이디

}
