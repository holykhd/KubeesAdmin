package com.kubees.admin.partners.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/partners/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("header", "statistics");
        return "partners/statistics/list";
    }
}
