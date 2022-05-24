package com.kubees.admin.api;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.sns.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BlockFlagChangeController {
    private final SnsService snsService;

    @PostMapping("/admin/sns/changeBlockFlagStatus/{fromUserId}/{toUserId}")
    public ResponseEntity changeBlockFlagStatus(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                @PathVariable Long fromUserId,
                                                @PathVariable Long toUserId) {
        // 관리자 계정(role_admin) 일 경우에만 취소할 수 있다.
        // To_user_id 를 가져와서 관리자일때만 삭제를 해라.
        snsService.changeBlockFlagStatus(principalDetails, fromUserId, toUserId);

        return null;
    }
}
