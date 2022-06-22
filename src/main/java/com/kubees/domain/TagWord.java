package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long feedId;                    // 피드 아이디

    @NotNull
    @Column(nullable = false, columnDefinition = "text")
    private String tag;                     // 태그

    @NotNull
    @Column(nullable = false)
    private String userEmail;                 // 태그 등록자

    private LocalDateTime createdAt;        // 태그 등록일

    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }
}
