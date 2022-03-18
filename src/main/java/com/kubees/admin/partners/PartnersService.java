package com.kubees.admin.partners;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.partners.form.PartnersForm;
import com.kubees.domain.Account;
import com.kubees.domain.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnersService {

    private final PartnersRepository partnersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;

    /**
     * 파트너 정보 등록하기
     */
    @Transactional
    public Partners createPartnerProcessor(PartnersForm partnersForm, PrincipalDetails principalDetails) {

        // 파트너 정보 등록하기(파트너 관리)
        Partners partners = new Partners();
        Partners newPartners = createNewPartner(partnersForm, principalDetails, partners);

        // 회원 정보에 파트너 정보 저장하기(회원 권한)
        Account account = new Account();
        createNewUser(partnersForm, account);

        return newPartners;
    }

    // 관리를 위해 비밀번호는 원문 그대로 저장한다.
    private Partners createNewPartner(PartnersForm partnersForm, PrincipalDetails principalDetails, Partners partners) {
        partners.setPartnerCompanyName(partnersForm.getPartnerCompanyName());
        partners.setPartnerId(partnersForm.getPartnerId());
        partners.setPartnerUserName(partnersForm.getPartnerUserName());
        partners.setPartnerPassword(partnersForm.getPartnerPassword());
        partners.setPartnerPhone(partnersForm.getPartnerPhone());
        partners.setPartnerEmail(partnersForm.getPartnerEmail());
        partners.setPartnerCreatedUserId(principalDetails.getUsername());
        Partners newPartners = partnersRepository.save(partners);
        return null;
    }

    private void createNewUser(PartnersForm partnersForm, Account account) {
        account.setUserId(partnersForm.getPartnerId());
        account.setEmail(partnersForm.getPartnerEmail());
//        account.setNickname(partnersForm.getPartnerNickname())
        account.setPassword(bCryptPasswordEncoder.encode(partnersForm.getPartnerPassword()));
        account.setName(partnersForm.getPartnerUserName());
        account.setPhone(partnersForm.getPartnerPhone());
//        account.setGender(partnersForm.getGender());
        account.setRoles("ROLE_MANAGER");
        accountRepository.save(account);
    }


    /**
     * 파트너 정보 수정하기
     */
    @Transactional
    public void updatePartnerProcessor(PartnersForm partnersForm, PrincipalDetails principalDetails) {

        // 회원정보를 수정 할 때는 회원 아이디는 변경하면 안된다!!!필수사항
        // 담당자 이메일은 변경을 할 수 있도록 수정을 해야한다.

        // 파트너 정보 수정하기(파트너 관리)
        Partners partners = partnersRepository.findByPartnerId(partnersForm.getPartnerId());
        updatePartner(partnersForm, principalDetails, partners);

        // 회원 정보에 파트너 정보 저장하기(회원 권한)
        Account account = accountRepository.findByUserId(partnersForm.getPartnerId());

        // 수정 할 때 수정할 이메일이 존재하는지 체크를 해 줘야할 것 같다(컨트롤러에서 체크해서 바인딩 에러처리하면 될 듯..
        updateUser(partnersForm, account);
    }

    private Partners updatePartner(PartnersForm partnersForm, PrincipalDetails principalDetails, Partners partners) {
        log.info("partnersForm ={}", partnersForm);
        log.info("partners.partnerId ={}", partners.getPartnerId());
        partners.setPartnerCompanyName(partnersForm.getPartnerCompanyName());

        // 회원 아이디는 수정할 유저의 아이디를 넣어준다.
        partners.setPartnerId(partners.getPartnerId());
        partners.setPartnerUserName(partnersForm.getPartnerUserName());
        partners.setPartnerPassword(partnersForm.getPartnerPassword());
        partners.setPartnerPhone(partnersForm.getPartnerPhone());
        partners.setPartnerEmail(partnersForm.getPartnerEmail());
        partners.setPartnerUpdate(LocalDateTime.now());
        partners.setPartnerUpdateUserId(principalDetails.getUsername());
        return null;
    }

    private void updateUser(PartnersForm partnersForm, Account account) {
        account.setUserId(partnersForm.getPartnerId());
        account.setEmail(partnersForm.getPartnerEmail());
        account.setPassword(bCryptPasswordEncoder.encode(partnersForm.getPartnerPassword()));
        account.setName(partnersForm.getPartnerUserName());
        account.setPhone(partnersForm.getPartnerPhone());
    }


    /**
     * 파트너 목록 가져오기
     */
    public List<Partners> getPartnerList() {
        return partnersRepository.findAll();
    }

    /**
     * 파트너 상세 정보 가져오기
     */
    public Partners getPartnerProcessor(String partnerId) {
        return partnersRepository.findByPartnerId(partnerId);
    }

    public PartnersForm getPartner(String partnerId) {
        Partners partners = partnersRepository.findByPartnerId(partnerId);

        PartnersForm partnersForm = settingsPartnersForm(partners);

        return partnersForm;
    }

    private PartnersForm settingsPartnersForm(Partners partners) {
        PartnersForm partnersForm = new PartnersForm();
        partnersForm.setPartnerCompanyName(partners.getPartnerCompanyName());
        partnersForm.setPartnerId(partners.getPartnerId());
        partnersForm.setPartnerUserName(partners.getPartnerUserName());
        partnersForm.setPartnerPassword(partners.getPartnerPassword());
        partnersForm.setPartnerPhone(partners.getPartnerPhone());
        partnersForm.setPartnerEmail(partners.getPartnerEmail());
        return partnersForm;
    }

}

