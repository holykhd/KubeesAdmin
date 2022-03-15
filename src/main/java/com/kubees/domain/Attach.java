package com.kubees.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_oid")
    private long attachOid;                     // 첨부파일 고유번호

    @Column(name = "user_email")
    private String userEmail;                   // 첨부파일 등록자 이메일

    @Column(name = "original_file_name")
    private String originalFileName;            // 첨부파일 원본 파일명

    @Column(name = "upload_type")
    private String uploadType;                 // 업로드 타입(profile, character, kubees)

    @Column(name = "file_ext")
    private String fileExt;                     // 파일 확장자명

    @Column(name = "saved_file_name")
    private String savedFileName;               // 첨부파일 저장 파일명

    @Column(name ="thumb_file_name")
    private String thumbFileName;               // 썸네일 파일명

    @Column(name = "saved_path")
    private String savedPath;                   // 첨부파일 저장 경로

    @Column(name = "file_size")
    private long fileSize;                      // 첨부파일 파일 크기

    @Column(name = "attach_created_at")
    private LocalDateTime attachCreatedAt;      // 첨부파일 등록일

}
