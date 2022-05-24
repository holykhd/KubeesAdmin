package com.kubees.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "video_id")
    private Long id;                        // video 고유 아이디

    @Column(name = "feed_id", nullable = false)
    private Long feedId;                    // 피드 고유 아이디

    @Column(nullable = false)
    private String videoId;                 // 비디오 아이디(비메오 API 등록 고유 번호)

    private String videoContent;            // 내용

    @Column(nullable = false)
    private String userEmail;               // 등록자 이메일

    private LocalDateTime createdAt;        // 등록일

//    @ManyToOne
//    @JoinColumn(name = "feed_id")
//    private Feed feed;

    @PrePersist
    private void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }
}
