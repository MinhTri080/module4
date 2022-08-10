package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
    @RequestMapping("car?/s?o?/infor1")
    public String infor1(Model model){
        model.addAttribute("message","Infor1");
        return "/index";
    }
    @RequestMapping("c*/s*d/infor2")
    public ModelAndView infor2(){
    ModelAndView model = new ModelAndView("/index");
    model.addObject("message","Infor2");
    return model;
    }
    @RequestMapping("/card/**")
    public ModelAndView infor3(){
        ModelAndView model = new ModelAndView("/index");
        model.addObject("message","Tri dao so vo");
        return model;
    }
    @RequestMapping(value = "/home",produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(){
        return "/cai ni de lam chi";
    }
}
