package com.kubees.admin.user.form;

import com.kubees.domain.enumType.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockForm {
    private Long id;
    private String userId;
    private String email;
    private String nickname;
    private Long profileId;       // 프로필 이미지 정보
    private Long characterId;     // 캐릭터 이미지 정보
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private String userDescription;
    private UserStatus userStatus;
    private Long fromUserId;
    private Long toUserId;
}
