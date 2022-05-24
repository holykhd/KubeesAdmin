package com.kubees.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
                name = "block_uk",
                columnNames = {"from_user_id", "to_user_id"}
        )
})
public class Block {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id")
    private Account fromUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id")
    private Account toUser;

    private LocalDateTime createdAt;

    @PrePersist
    void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }

}
