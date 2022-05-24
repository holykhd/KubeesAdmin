package com.kubees.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;


@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "feed_id")
    private Long id;                                    // 고유 아이디

    //    @JsonIgnoreProperties({"feed"})
    @JsonManagedReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String userEmail;                           // 회원 이메일 정보

    private String nickname;                            // 회원 닉네임(글 작성 후 닉네임 변경 시 새로운 글부터 변경된 닉네임 적용)

    @Column(name = "character_id")
    @ColumnDefault("0")
    private Long characterId;                       // 캐릭터 이미지

    // 피드의 좋아요 가져오기
    @OneToMany(mappedBy = "feed")
    private List<FeedLike> likes;

    @Column(name = "profile_id")
    @ColumnDefault("0")
    private Long profileId;                         // 개인 이미지

    private String contents;                            // 게시글 내용

    @Column(name = "bg_color")
    private String bgColor;                 // 배경 이미지(이미지가 없을경우 배경 이미지 저장

//    @OneToMany(mappedBy = "feed")
//    @ManyToMany
//    private List<Video> videoId = new ArrayList<>();     // video 동영상 업로드 아이디

    private String tags;                                // 태그정보(차후 수정을 할 수 있다)

    private int likeCnt;                            // 좋아요 수

    private String location;                            // 위치정보(차후 추가작업 필요)

    private boolean switchLikeAndHits;                   // 좋아요 및 조회수 보이기/숨기기(숨기기시 본인만 보여진다.)(true : 좋아요 보이기 / false : 좋아요 숨기기)

    private boolean switchReply;                          // 댓글 보이기 숨기기(true : 댓글 보이기 / false : 댓글 숨기기)

    private LocalDateTime createdAt;                     // 등록일

    private String updateEmail;                          // 수정자 이메일

    private boolean feedStatus;                         // 피드 상태(true : 보이기 / false : 숨기기)

    private LocalDateTime updatedAt;                     // 수정한 날짜

    private boolean delState;                            // 피드 삭제 여부(Y : 삭제 / N : 삭제안됨)
    private String delUserId;                           // 삭제자 아이디
    private LocalDateTime delAt;                        // 삭제일

    @Transient
    private boolean likeState;

    @PrePersist
    private void LocalDatetime() {
        this.createdAt = LocalDateTime.now();
        this.feedStatus = true;             // 피드 상태는 처음에는 무조건 보이게 하기
        this.likeCnt = 0;
        this.delState = false;
    }
}
