package com.kubees.admin.partners;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.partners.form.EmailDuplicatedForm;
import com.kubees.admin.partners.form.PartnersForm;
import com.kubees.admin.partners.form.PartnersFormValidator;
import com.kubees.admin.partners.form.SearchForm;
import com.kubees.domain.Partners;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    public String list(Model model, SearchForm searchForm,
                       @PageableDefault(size = 10, sort = "partnerCreated", direction = Sort.Direction.DESC)
                               Pageable pageable) {
        model.addAttribute("searchForm", searchForm);
        Page<Partners> partnerList = partnersService.getPartnerList(searchForm, pageable);
        model.addAttribute("partnerList", partnerList);

        return "partners/list";
    }

    /**
     * 파트너 상세정보
     */
    @GetMapping("/detail/{partnerId}")
    public String detail(@PathVariable String partnerId, Model model, PartnersForm partnersForm) {
        Partners partnerDetail = partnersService.getPartnerProcessor(partnerId);
        model.addAttribute("partnerDetail", partnerDetail);

        return "partners/detail";
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
     * 파트너 등록
     */
    @PostMapping("/form")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid PartnersForm partnersForm, BindingResult bindingResult) {
        partnersForm.setPartnerCreatedUserId(principalDetails.getAccount().getUserId());
        if (bindingResult.hasErrors()) {
            log.info("partners error ={}", bindingResult.getAllErrors());
            return "partners/form";
        }

        partnersService.createPartnerProcessor(partnersForm, principalDetails);

        return "redirect:/admin/partners/list";
    }

    /**
     * 파트너 정보 수정
     */
    @GetMapping("/edit/{partnerId}")
    public String editForm(@PathVariable String partnerId, Model model) {
        PartnersForm partnersForm = partnersService.getPartner(partnerId);
        model.addAttribute("partnersForm", partnersForm);
        return "partners/edit";
    }

    /**
     * 파트너 정보 수정하기
     */
    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       PartnersForm partnersForm,
                       @Valid EmailDuplicatedForm emailDuplicatedForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "partners/edit";
        }

        partnersForm.setPartnerUpdateUserId(principalDetails.getAccount().getUserId());
        partnersService.updatePartnerProcessor(partnersForm, principalDetails);

        return "partners/list";
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
