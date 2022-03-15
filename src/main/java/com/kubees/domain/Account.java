package com.kubees.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_id")
    private String userId;                      // 회원 아이디

    @Column(unique = true)
    private String email;                       // 이메일

    private String nickname;                    // 닉네임

    private String password;                    // 비밀번호

    private String name;                        // 이름

    private String phone;                       // 전화번호

    private String gender;                       // 성별

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;            // 최근 접속시간

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;               // 계정상태 : NOT_CHECKED(이메일 인증안됨), DISABLED(비활성화), ACTIVE(활성화), BLOCK(차단)
    //Y : 정상 유저, D : 탈퇴한 유저, B : 이용 정지된 유저, T : 탈퇴 유예 상태인 유저, P : 이용 정지 유예 상태인 유저, M : 유실된 계정

    @Column(name = "user_description")
    private String userDescription;                    // 소개문구

    @Column(name = "character_id")
    @ColumnDefault("0")
    private Long characterId;                       // 캐릭터 이미지

    @Column(name = "profile_id")
    @ColumnDefault("0")
    private Long profileId;                         // 개인 이미지

    @Column(name = "created_at")
    private LocalDateTime createdAt;            // 등록일

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;           // 수정일

    @Column(name = "status_modified_at")
    private LocalDateTime statusModifiedAt;     // 상태 변경일

    @Column(name = "marketing_agree")
    private String marketingAgree;              // 마케팅 수신 여부

    @Column(name = "user_role")
    private String roles;                  // 회원 권한(일반회원, 매니저, 관리자)

    public List<String> getRolesList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    // 초기 가입시 기번 설정값들
    // 가입날짜를 현재시간으로, 캐릭터 아이디와 프로필 아이디를 0
    // 회원 권한을 ROLE_USER, 회원 상태를 정상회원으로 설정
    @PrePersist
    public void LocalDateTime() {
        this.createdAt = LocalDateTime.now();
        this.characterId = 0L;
        this.profileId = 0L;
        this.roles = "ROLE_USER";
        this.userStatus = UserStatus.Y;
    }



    // 구글 로그인 값 : idPCode(google), regDate(123123423523), userId(L6468KRJGDKJKJB6), lastLoginDate(123123423523)
    private String snsType;

    public void completedLogin() {
        createdAt = LocalDateTime.now();
    }}
