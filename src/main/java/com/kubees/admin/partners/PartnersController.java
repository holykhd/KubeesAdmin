package com.kubees.admin.partners;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.partners.form.PartnersForm;
import com.kubees.admin.partners.form.PartnersFormValidator;
import com.kubees.domain.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/partners/")
@RequiredArgsConstructor
public class PartnersController {

    private final PartnersService partnersService;
    private final PartnersFormValidator partnersFormValidator;

    @InitBinder("partnersForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(partnersFormValidator);
    }

    /**
     * 파트너 목록 조회
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<Partners> partnerList = partnersService.getPartnerList();
        model.addAttribute("partnerList", partnerList);

        return "partners/list";
    }

    /**
     * 등록화면으로 이동
     */
    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute(new PartnersForm());
        return "partners/form";
    }

    /**
     *
     * 파트너 등록
     */
    @PostMapping("/form")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid PartnersForm partnersForm, BindingResult bindingResult) {
        log.info("(partnersFormSSSS ={}", partnersForm);
        partnersForm.setPartnerCreatedUserId(principalDetails.getAccount().getUserId());
        if (bindingResult.hasErrors()) {
            return "partners/form";
        }

        log.info("create Form start ={}", partnersForm);

        Partners newPartners = partnersService.createPartnerProcessor(partnersForm, principalDetails);
        log.info("newPartners = {}", newPartners);

        return "redirect:/admin/partners/list";
    }

    /**
     * 파트너 정보 수정
     */
    @GetMapping("/edit")
    public String edit() {
        return "form";
    }

    /**
     * 상품 목록
     */
    @GetMapping("/product/list")
    public String productList() {
        return "partners/product/list";
    }

    /**
     * 상품 수정
     */
    @GetMapping("/product/edit")
    public String productEditForm() {
        return "partners/product/edit";
    }

}
