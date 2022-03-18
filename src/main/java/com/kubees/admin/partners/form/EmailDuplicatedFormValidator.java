package com.kubees.admin.partners.form;

import com.kubees.admin.account.AccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailDuplicatedFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(EmailDuplicatedForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailDuplicatedForm emailDuplicatedForm = (EmailDuplicatedForm) target;

        // DB에서 조회한 회원 아이디와 수정할 아이디가 같이 않을 경우 오류 메시지 출력
        if (!accountRepository.findByUserId(emailDuplicatedForm.getUserId()).equals(emailDuplicatedForm.getUserId())) {
            errors.rejectValue("userId", "invalid.userId", new Object[]{emailDuplicatedForm.getEmail()}, "회원 정보가 일치하지 않습니다.");
        }
    }
}
