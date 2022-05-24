package com.kubees.admin.partners.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/partners/products")
@RequiredArgsConstructor
public class ProductsController {

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("header", "products");
        return "partners/product/list";
    }
}
