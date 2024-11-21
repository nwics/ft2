package com.example.ft2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login/")
public class LoginController {


    @GetMapping("indexapi")
    public ModelAndView indexapi() {
        ModelAndView view = new ModelAndView("login/indexapi");
        return view;
    }
}
