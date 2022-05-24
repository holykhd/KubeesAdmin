package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // 댓글 고유 아이디

    private String userEmail;                   // 글쓴이 이메일

    private String nickname;                    // 회원 닉네임(글 작성 후 닉네임 변경 시 새로운 글부터 변경된 닉네임 적용)

    @Column(name = "character_id")
    @ColumnDefault("0")
    private Long characterId;                       // 캐릭터 이미지

    @Column(name = "profile_id")
    @ColumnDefault("0")
    private Long profileId;                         // 개인 이미지

    private Long feedId;                        // 피드 아이디

    private String cubeId;                      // 큐브 아이디

    private String comment;                     // 댓글 내용

    private LocalDateTime createdAt;            // 등록일

    private LocalDateTime updatedAt;            // 수정일

    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }
}
