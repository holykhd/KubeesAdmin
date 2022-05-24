package com.kubees.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//@Entity
//@Getter
//@Setter
//@EqualsAndHashCode(of = "id")
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String userEmail;

    @NotNull
    @Column(name = "feed_id")
    private Long feedId;

    private LocalDateTime createdAt;

    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
    }
}
