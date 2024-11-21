package com.example.ft2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blood/")
public class BloodController {
    
    @GetMapping("indexapi")
    public String index(){
        return "blood/indexapi";
    }
}
