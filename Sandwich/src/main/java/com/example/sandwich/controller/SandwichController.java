package com.example.sandwich.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SandwichController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @PostMapping("/save")
    public String save(@RequestParam("condiment") String[] condiment , Model model) {
        model.addAttribute("condiment",condiment);
        return "index";
    }
}
