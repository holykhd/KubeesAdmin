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
                name = "subscribe_uk",
                columnNames = {"from_user_id", "to_user_id"}
        )
})
public class Subscribe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id")
    private Account fromUser;           // 구독하는 유저

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id")
    private Account toUser;             // 구독 받는 유저

    private LocalDateTime createdAt;    // 구독일

    @PrePersist
    void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }
}
