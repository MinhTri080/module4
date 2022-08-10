package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.CustomerService;
import com.cg.service.impl.HibernateCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private HibernateCustomerServiceImpl hibernateCustomerService;


    @GetMapping
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showInformation(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        Customer customer = customerService.findOne(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreatePage(){
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("/create");

    Customer customer = new Customer();
    modelAndView.addObject("customer",customer);
    return modelAndView;
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer){
    customerService.save(customer);
    return "redirect:/customers";
    }
    @PostMapping("/update")
    public String doUpdate(@ModelAttribute Customer customer) {
        System.out.println(customer);
        customerService.save(customer);
        return "redirect:/customers";
    }

}
