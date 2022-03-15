package com.kubees.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "profile_image")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;                      // 회원 아이디

    @Lob
    @Basic(fetch = EAGER)
    @Column(name = "character_image")
    private String characterImage;              // 캐릭터 이미지

    @Lob
    @Basic(fetch = EAGER)
    @Column(name = "profile_image")
    private String profileImage;                // 개인 이미지

    @Column(name = "created_at")
    private LocalDateTime createdAt;            // 등록일

}
