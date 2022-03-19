package com.kubees.admin.partners;

import com.kubees.admin.account.AccountRepository;
import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.partners.form.PartnersForm;
import com.kubees.admin.partners.form.SearchForm;
import com.kubees.domain.Account;
import com.kubees.domain.Partners;
import com.kubees.domain.QPartners;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.kubees.domain.QAccount.account;
import static com.kubees.domain.QPartners.partners;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnersService {

    private final PartnersRepository partnersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

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
    @Transactional
    public Page<Partners> getPartnerList(SearchForm searchForm, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        QueryResults<Partners> results = queryFactory.selectFrom(partners)
                .where(searchTypeContain(searchForm.getSearchType(), searchForm.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Partners> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    // partnerCompanyName(파트너 업체명), partnerId(파트너 아이디), partnerUserName(파트너 이름), partnerPhone(파트너 연락처)
    private BooleanExpression searchTypeContain(String searchTypeCondition, String keywordCondition) {
        BooleanExpression booleanExpression = null;
        keywordCondition = keywordCondition != null ? keywordCondition : "";

        if (searchTypeCondition != null && searchTypeCondition.equals("partnerCompanyName")) {
            booleanExpression = searchTypeCondition != null ? partners.partnerCompanyName.contains(keywordCondition) : null;
        }
        if (searchTypeCondition != null && searchTypeCondition.equals("partnerId")) {
            booleanExpression = searchTypeCondition != null ? partners.partnerId.contains(keywordCondition) : null;
        }
        if (searchTypeCondition != null && searchTypeCondition.equals("partnerUserName")) {
            booleanExpression = searchTypeCondition != null ? partners.partnerUserName.contains(keywordCondition) : null;
        }
        if (searchTypeCondition != null && searchTypeCondition.equals("partnerPhone")) {
            booleanExpression = searchTypeCondition != null ? partners.partnerPhone.contains(keywordCondition) : null;
        }
        return booleanExpression;
    }

    /**
     * 파트너 상세 정보 가져오기
     */
    @Transactional
    public Partners getPartnerProcessor(String partnerId) {
        return partnersRepository.findByPartnerId(partnerId);
    }

    @Transactional
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

