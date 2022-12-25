package com.mustache.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MustacheController {

    //test용
    @GetMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("username", "dhlee");
        return "greetings";
    }
}
