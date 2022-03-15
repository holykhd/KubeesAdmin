package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Visit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "visit_email")
    private String visitEmail;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @PrePersist
    public void LocalDateTime() {
        this.createDate = LocalDateTime.now();
    }
}
