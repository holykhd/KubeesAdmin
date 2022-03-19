package com.kubees.admin.qna;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.qna.form.QnAForm;
import com.kubees.domain.QnA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String userList(Model model) {
        List<QnA> qnaList = qnAService.getQnaListProcessor();
        log.info("qnaList ={}", qnaList);
        model.addAttribute("qnaList", qnaList);
        return "qna/list";
    }

    /**
     * QnA 상세 화면 조회
     */
    @GetMapping("/detail")
    public String userDetail() {
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
