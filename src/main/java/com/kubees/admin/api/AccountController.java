package com.kubees.admin.api;

import com.kubees.admin.exception.DefaultResponse;
import com.kubees.admin.exception.ResponseMessage;
import com.kubees.admin.user.UserService;
import com.kubees.admin.user.form.BlockForm;
import com.kubees.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    /**
     * 회원이 차단한 회원 목록
     */
    @GetMapping("/admin/user/blockToUserList/{id}")
    public ResponseEntity userBlockToUserList(@PathVariable Long id) {
        List<BlockForm> blockToUserList = userService.getBlockToUserList(id);
        return new ResponseEntity(new DefaultResponse<>(ResponseMessage.SEARCH_BLOCK_LIST, blockToUserList), HttpStatus.OK);
    }
}
