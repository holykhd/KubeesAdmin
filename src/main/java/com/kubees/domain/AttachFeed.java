package com.kubees.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AttachFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_feed_oid")
    private Long attachFeedOid;                     // 첨부파일 고유번호

    @Column(name = "feed_id")
    private Long feedId;                       // 피드 아이디

    @Column(name = "video_id")
    private String videoId;                 // 비디오 아이디(비메오 API 등록 고유 번호)

    @Column(name = "cube_id")
    private Long cubeId;                      // 큐브 아이디

    @Column(name = "user_email")
    private String userEmail;                   // 첨부파일 등록자 이메일

    @Column(name = "original_file_name")
    private String originalFileName;            // 첨부파일 원본 파일명

    @Column(name = "upload_type")
    private String uploadType;                 // 업로드 타입(image, movie)

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

    private String sort;                     // 피드 내용 중 이미지/비디오 등록 순서(등록 순서대로 상세에서 보여주기 위함)

    @Column(name = "attach_created_at")
    private LocalDateTime attachCreatedAt;      // 첨부파일 등록일

    @PrePersist
    public void createDate() {
        this.attachCreatedAt = LocalDateTime.now();
    }

}
