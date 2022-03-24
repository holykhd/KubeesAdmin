package com.kubees.admin.pushMessage;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.admin.pushMessage.form.PushMessageForm;
import com.kubees.admin.pushMessage.form.SearchForm;
import com.kubees.domain.PushMessage;
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
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/pushMessage/")
@RequiredArgsConstructor
public class PushMessageController {

    private final PushMessageService pushMessageService;
    private final PushMessageRepository pushMessageRepository;


    /**
     * 푸시메시지 목록 조회
     */
    @GetMapping("/list")
    public String list(Model model, SearchForm searchForm,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PushMessage> pushMessageList =  pushMessageService.getPushMessageProcessor(searchForm, pageable);
        model.addAttribute("pushMessageList", pushMessageList);
        return "pushMessage/list";
    }

    /**
     * 푸시메시지 등록 화면
     */
    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("pushMessageForm", new PushMessageForm());
        return "pushMessage/form";
    }

    /**
     * 푸시메시지 등록
     */
    @PostMapping("/form")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid PushMessageForm pushMessageForm, BindingResult bindingResult) {
        log.info("pushMessageForm ={}", pushMessageForm);
        pushMessageService.createPushMessageProcessor(principalDetails, pushMessageForm);

        return "redirect:/admin/pushMessage/list";
    }

    /**
     * 푸시메시지 상세 화면 조회
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        PushMessage pushMessageDetail = pushMessageRepository.findById(id).orElse(null);
        model.addAttribute("detail", pushMessageDetail);
        return "pushMessage/detail";
    }

    /**
     * 푸시메시지 수정 화면
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return "pushMessage/edit";
    }


    /**
     * 푸시메시지 수정
     */
    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid PushMessageForm pushMessageForm, BindingResult bindingResult) {
        return "pushMessage/edit";
    }
}
