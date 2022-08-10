package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners/{ownersId}")
public class Demo {
//    @GetMapping("/home")
//    @ResponseBody
//    public String demo(@RequestParam String message){
//
//        return message;
//    }
    @GetMapping("/pet/{petId}")
    public String findPet(@PathVariable Long ownersId,@PathVariable Long petId){

        return "/home";
    }
}
