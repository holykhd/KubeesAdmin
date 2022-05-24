package com.kubees.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "feedLike_uk",
                columnNames = {"feed_id", "like_user_id"}
        )
})
public class FeedLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;           // 내 피드 유저

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "like_user_id")
    private Account likeUser;             // 나를 좋아요 하는 유저

    private LocalDateTime createdAt;    // 좋아요 등록일

    @PrePersist
    void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }

}
