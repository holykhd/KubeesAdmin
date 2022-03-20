package com.kubees.admin.qna;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.qna.form.QnAForm;
import com.kubees.admin.qna.form.SearchForm;
import com.kubees.domain.Account;
import com.kubees.domain.QnA;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/qna/")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    /**
     * QnA 목록 조회
     */
    @GetMapping("/list")
    public String qnaList(Model model, SearchForm searchForm, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("searchForm", searchForm);
        Page<QnA> qnaList = qnAService.getQnaListProcessor(searchForm, pageable);
        log.info("qnaListList ={}", qnaList);
        model.addAttribute("qnaList", qnaList);
        return "qna/list";
    }

    /**
     * QnA 상세 화면 조회
     */
    @GetMapping("/detail")
    public String qnaDetail() {
        return "qna/detail";
    }

    /**
     * QnA 수정 화면
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        QnA qnA = qnAService.getQnaProcessor(id);
        model.addAttribute(new QnAForm());
        model.addAttribute("qna", qnA);
        return "qna/edit";
    }

    /**
     * QnA 수정하기
     */
    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @Valid QnAForm qnAForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "qna/edit";
        }



        return "redirect:/admin/qna/list";

    }

}
