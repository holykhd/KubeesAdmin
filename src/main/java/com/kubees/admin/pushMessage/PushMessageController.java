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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
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
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws ParseException {
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
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid PushMessageForm pushMessageForm, BindingResult bindingResult) throws ParseException {
        if (pushMessageForm.getPublishTime().equals("reservation") &&
                (pushMessageForm.getPublishDate() == null
                        || pushMessageForm.getPublishHour().length() < 2
                        || pushMessageForm.getPublishMinutes().length() < 2)) {

            pushMessageForm.setMessage("예약 등록을 하실 경우 예약 날짜와 시간은 필수 입력 사항입니다.");
//            return "redirect:/admin/notice/form";
            return "pushMessage/form";
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingError ={}", bindingResult.getAllErrors());
            return "pushMessage/form";
        }
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
        PushMessage pushMessageDetail = pushMessageRepository.findById(id).orElse(null);
        model.addAttribute("pushMessageForm", pushMessageDetail);
        return "pushMessage/edit";
    }


    /**
     * 푸시메시지 수정
     */
    @PostMapping("/edit/{id}")
    public String edit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @PathVariable Long id,
                       @Valid PushMessageForm pushMessageForm, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return "pushMessage/edit";
        }
        pushMessageService.editPushMessageProcessor(principalDetails, pushMessageForm, id);
        return "redirect:/admin/pushMessage/list";
    }

    /**
     * 푸시메시지 삭제
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        pushMessageService.deleteProcessor(id);
        return "redirect:/admin/pushMessage/list";
    }
}
