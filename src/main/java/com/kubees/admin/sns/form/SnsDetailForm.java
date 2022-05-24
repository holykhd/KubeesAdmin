package com.kubees.admin.sns.form;

import com.kubees.domain.enumType.UserStatus;
import lombok.Data;

@Data
public class SnsDetailForm {
    private String email;           // 사용자 계정(이메일)
    private String name;            // 사용자 이름
    private Long profileId;       // 프로필 이미지 정보
    private Long characterId;     // 캐릭터 이미지 정보
    private String userId;          // 사용자 아이디
    private String userDescription; // 소개문구
    private UserStatus userStatus;      // 차단여부
}
