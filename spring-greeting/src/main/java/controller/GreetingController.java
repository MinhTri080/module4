package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @GetMapping("/hello")
    public String handle(Model model) {
        model.addAttribute("message", "Hello Motor");

        return "/index";
    }

}
