package com.kubees.admin.partners.form;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.partners.PartnersRepository;
import com.kubees.domain.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartnersFormValidator implements Validator {

    private final PartnersRepository partnersRepository;
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PartnersForm.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        PartnersForm partnersForm = (PartnersForm) object;
        if (partnersRepository.existsByPartnerId(partnersForm.getPartnerId())) {
            errors.rejectValue("partnerId", "invalid.partnerId", new Object[]{partnersForm.getPartnerId()}, "이미 등록되어 있는 아이디 입니다.");
        }

        if (accountRepository.existsByUserId(partnersForm.getPartnerId())) {
            errors.rejectValue("partnersId", "invalid.partnerId", new Object[]{partnersForm.getPartnerId()}, "이미 등록되어 있는 아이디 입니다.");
        }
    }
}