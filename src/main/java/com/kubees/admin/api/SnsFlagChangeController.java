package com.kubees.admin.api;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.exception.DefaultResponse;
import com.kubees.admin.exception.ResponseMessage;
import com.kubees.admin.sns.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SnsFlagChangeController {
    private final SnsService snsService;

    @PostMapping("/admin/sns/changeUserStatus/{email}/{userStatus}")
    public ResponseEntity userStatus(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PathVariable String email,
                                     @PathVariable String userStatus) {
        Map<String, Object> map = snsService.changeUserStatus(principalDetails, email, userStatus);
        return new ResponseEntity(new DefaultResponse<>(ResponseMessage.CHANGE_SNS_FLAG_SUCCESS, map), HttpStatus.OK);
    }

}
