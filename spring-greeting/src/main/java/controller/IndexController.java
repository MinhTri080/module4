package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class IndexController {
    @RequestMapping("/")
    public String get(Model model){
        model.addAttribute("message" , "Trí Lê");
        return "/home";
    }
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("message","ASDFGHJKL;'");
    return "/index";
    }
}
