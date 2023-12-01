package com.example.dressing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {

    // 카테고리를 total을 선택한 경우 (default total)
    @GetMapping("/category/total")
    public String showMainCategoryTotal(HttpSession session) {
        session.setAttribute("category", "total");
        return "redirect:/main";
    }

    // 카테고리를 outer을 선택한 경우
    @GetMapping("/category/outer")
    public String showMainCategoryOuter(HttpSession session) {
        session.setAttribute("category", "outer");
        return "redirect:/main";
    }

    // 카테고리를 top을 선택한 경우
    @GetMapping("/category/top")
    public String showMainCategoryTop(HttpSession session) {
        session.setAttribute("category", "top");
        return "redirect:/main";
    }

    // 카테고리를 bottom을 선택한 경우
    @GetMapping("/category/bottom")
    public String showMainCategoryBottom(HttpSession session) {
        session.setAttribute("category", "bottom");
        return "redirect:/main";
    }

    // 카테고리를 shoes를 선택한 경우
    @GetMapping("/category/shoes")
    public String showMainCategoryShoes(HttpSession session) {
        session.setAttribute("category", "shoes");
        return "redirect:/main";
    }

    // 카테고리를 bag을 선택한 경우
    @GetMapping("/category/bag")
    public String showMainCategoryBag(HttpSession session) {
        session.setAttribute("category", "bag");
        return "redirect:/main";
    }

    // 카테고리를 like를 선택한 경우
    @GetMapping("/category/like")
    public String showMainCategoryLike(HttpSession session) {
        session.setAttribute("category", "like");
        return "redirect:/main";
    }

    // 카테고리를 dislike를 선택한 경우
    @GetMapping("/category/dislike")
    public String showMainCategoryDislike(HttpSession session) {
        session.setAttribute("category", "dislike");
        return "redirect:/main";
    }
}