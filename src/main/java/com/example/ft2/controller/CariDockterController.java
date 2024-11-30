package com.example.ft2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dokter/")
public class CariDockterController {

    @GetMapping("caridokter")
    public String cariDokter(){
        return "caridokter/index";
    }

}
