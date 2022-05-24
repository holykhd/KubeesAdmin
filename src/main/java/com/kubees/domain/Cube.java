package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
@Entity
public class Cube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;                        // 고유 아이디

    private Long cubeId;                    // 큐브 아이디

    private String email;                   // 회원 이메일

    private String nickname;                // 회원 닉네임(글 작성 후 닉네임 변경 시 새로운 글부터 변경된 닉네임 적용)

    @Column(name = "character_id")
    @ColumnDefault("0")
    private Long characterId;                       // 캐릭터 이미지

    @Column(name = "profile_id")
    @ColumnDefault("0")
    private Long profileId;                         // 개인 이미지

    private boolean cubeStatus;             // 큐브 상태(true : 보이기, false : 숨기기)

    private String title;                   // 큐브 제목

    private String description;             // 큐브 설명

    private LocalDateTime createAt;         // 큐브 생성일
}
