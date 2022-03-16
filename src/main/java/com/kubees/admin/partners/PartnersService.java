package com.kubees.admin.partners;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.partners.form.PartnersForm;
import com.kubees.domain.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PartnersService {

    private final PartnersRepository partnersRepository;


    @Transactional
    public Partners createPartnerProcessor(PartnersForm partnersForm, PrincipalDetails principalDetails) {
        Partners partners = new Partners();
        partners.setPartnerCompanyName(partnersForm.getPartnerCompanyName());
        partners.setPartnerId(partnersForm.getPartnerId());
        partners.setPartnerUserName(partnersForm.getPartnerUserName());
        partners.setPartnerPhone(partnersForm.getPartnerPhone());
        partners.setPartnerEmail(partnersForm.getPartnerEmail());
        partners.setPartnerCreatedUserId(principalDetails.getUsername());
        Partners newPartners = partnersRepository.save(partners);
        log.info("new Partners ={}", newPartners);
        return newPartners;
    }

    public List<Partners> getPartnerList() {
        return partnersRepository.findAll();
    }
}
