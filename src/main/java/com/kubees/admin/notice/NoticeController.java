package com.kubees.admin.notice;

import com.kubees.admin.auth.PrincipalDetails;
import com.kubees.admin.notice.form.NoticeForm;
import com.kubees.admin.notice.form.SearchForm;
import com.kubees.admin.util.Script;
import com.kubees.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/admin/notice/")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/test")
    public void test(String time) throws Exception {
        String date = "2012-02-08 11:13:42,570";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        Date date1 = simpleDateFormat.parse(date);
        log.info("date1 ={}", date1.getTime());

        String newTime = "2022-01-01 10:11:00";
        String format2 = new SimpleDateFormat(newTime).format(System.currentTimeMillis());
        log.info("format2 ={}", format2);

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String nowDate = sdf.format(new Date());
        log.info("nowDate ={}", nowDate);
        Date date2 = sdf.parse(nowDate);
        log.info("getTime ={}", date2.getTime()
        );


    }

    /**
     * ?????? : list
     * ?????? : detail
     * ???????????? : form
     * ?????? : form
     * ?????? : edit
     */


    /**
     * ???????????? ?????? ??????
     * ???????????? ??? ?????? ????????? ???????????? ????????????, ????????? ?????????????????? ????????? ???
     * ?????? ????????? ????????? ???????????? ?????? ???????????? ??????????????? ????????? open_flag?????? N?????? Y??? ????????????.
     */
    @GetMapping("/list")
    public String noticeList(Model model, SearchForm searchForm,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws ParseException {

        model.addAttribute("searchForm", searchForm);
        Page<Notice> noticeList = noticeService.getNoticeProcessor(searchForm, pageable);
        model.addAttribute("list", noticeList);
        return "notice/list";
    }

    /**
     * ???????????? ?????? ??????
     */
    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "notice/form";
    }

    /**
     * ???????????? ??????
     */
    @PostMapping("/form")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         @Valid NoticeForm noticeForm, BindingResult bindingResult, Model model) throws ParseException {
        model.addAttribute("noticeForm", noticeForm);
        log.info("noticeForm ={}", noticeForm);

        if (noticeForm.getPublishTime().equals("reservation")) {
            if (noticeForm.getPublishDate() == null
                    || noticeForm.getPublishHour().length() < 2
                    || noticeForm.getPublishMinutes().length() < 2) {
                noticeForm.setMessage("?????? ????????? ?????? ?????? ?????? ????????? ????????? ?????? ?????? ???????????????.");
                return "notice/form";
            }
        }
/*
        if (noticeForm.getPublishTime().equals("reservation") && (noticeForm.getPublishDate() == null
                                                                    || noticeForm.getPublishHour().length() < 2
                                                                    || noticeForm.getPublishMinutes().length() < 2)) {

            noticeForm.setMessage("?????? ????????? ?????? ?????? ?????? ????????? ????????? ?????? ?????? ???????????????.");
//            return "redirect:/admin/notice/form";
            return "notice/form";
        }
 */

        if (bindingResult.hasErrors()) {
            log.info("hasErrors ={}", bindingResult.getAllErrors());
            return "notice/form";
        }

        noticeService.createNoticeProcessor(noticeForm, principalDetails);

        return "redirect:/admin/notice/list";
    }

    /**
     * ???????????? ?????? ?????? ??????
     */
    @GetMapping("/detail/{id}")
    public String noticeDetail(@PathVariable Long id, Model model) {
        log.info("detail id ={}", id);
        Notice noticeDetail = noticeService.getNoticeDetailProcessor(id);
        log.info("noticeDetail ={}", noticeDetail);
        model.addAttribute("noticeDetail", noticeDetail);

        return "notice/detail";
    }

    /**
     * ???????????? ?????? ??????
     */
    @GetMapping("/edit/{id}")
    public String noticeEditForm(@PathVariable Long id, Model model) {
        NoticeForm noticeForm = noticeService.findById(id);
        model.addAttribute("noticeForm", noticeForm);
        return "notice/edit";
    }


    /**
     * ???????????? ??????
     */
    @PostMapping("/edit")
    public String noticeEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid NoticeForm noticeForm, BindingResult bindingResult) throws ParseException {

        noticeService.updateNoticeProcessor(principalDetails, noticeForm);
        log.info("notice Edit");
        return "redirect:/admin/notice/list";
    }

    /**
     * ???????????? ??????
     */
    @PostMapping("/delete/{id}")
    public String noticeDelete(@PathVariable Long id) {
        noticeService.deleteNoticeProcessor(id);
        return "redirect:/admin/notice/list";
    }

}
