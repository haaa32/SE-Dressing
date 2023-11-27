package com.example.dressing.dto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {

    @GetMapping("/category/total")
    public String showMainCategoryTotal(HttpSession session) {
        session.setAttribute("category", "total");
        return "redirect:/main";
    }

    @GetMapping("/category/outer")
    public String showMainCategoryOuter(HttpSession session) {
        session.setAttribute("category", "outer");
        return "redirect:/main";
    }

    @GetMapping("/category/top")
    public String showMainCategoryTop(HttpSession session) {
        session.setAttribute("category", "top");
        return "redirect:/main";
    }

    @GetMapping("/category/bottom")
    public String showMainCategoryBottom(HttpSession session) {
        session.setAttribute("category", "bottom");
        return "redirect:/main";
    }

    @GetMapping("/category/shoes")
    public String showMainCategoryShoes(HttpSession session) {
        session.setAttribute("category", "shoes");
        return "redirect:/main";
    }

    @GetMapping("/category/bag")
    public String showMainCategoryBag(HttpSession session) {
        session.setAttribute("category", "bag");
        return "redirect:/main";
    }
}
