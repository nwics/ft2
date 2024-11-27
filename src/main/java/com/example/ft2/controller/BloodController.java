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
// @Controller
// @RequestMapping("/admin/")
// public class AdminController {

//     @GetMapping("bloodgroup")
//     public String getBloodGroup() {
//         return "/blood_group/blood_group.html";
//     }
    
// }