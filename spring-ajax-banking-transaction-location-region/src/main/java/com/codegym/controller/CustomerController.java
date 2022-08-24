package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.service.customer.CustomerService;
import com.codegym.service.deposit.DepositService;
import com.codegym.service.transfer.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private DepositService depositService;
    @Autowired
    private TransferService transferService;


    @GetMapping
    public ModelAndView showListPage() {
//        ModelAndView modelAndView = new ModelAndView("customer/list");
//        Iterable<Customer> customers = customerService.findAll();
//        modelAndView.addObject("customers", customers);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/list");


        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/create");
        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView doCreate(@ModelAttribute Customer customer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/create");

        customer.setId(0L);
        customer.setBalance(new BigDecimal(0L));
        customerService.save(customer);

        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }

    @GetMapping("/deposit/{customerId}")
    public ModelAndView showDepositPage(@PathVariable Long customerId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/deposit");

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }

        modelAndView.addObject("customer", customerOptional.get());
        modelAndView.addObject("deposit", new Deposit());


        return modelAndView;
    }
    @GetMapping("/transfer")
    public ModelAndView showTransferPage(@PathVariable Long senderId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list");

        Optional<Customer> senderOptional = customerService.findById(senderId);
        if(!senderOptional.isPresent()){
            modelAndView.setViewName("error/404");
            return modelAndView;
        }
        List<Customer> recipients = customerService.findByIdIsNot(senderId);
        modelAndView.addObject("sender",senderOptional.get());
        modelAndView.addObject("recipients",recipients);
        modelAndView.addObject("transfer",new Transfer());

        return modelAndView;
    }

//    @GetMapping("customer/update")
//    public ModelAndView showUpdatePage(@PathVariable Long customerId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.
//        return modelAndView;
//    }

    @PostMapping("/deposit/{customerId}")
    public ModelAndView doCreate(@PathVariable Long customerId, @ModelAttribute Deposit deposit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/deposit");

        Optional<Customer> customerOptional = customerService.findById(customerId);

        Customer customer = customerOptional.get();

        BigDecimal currentBalance = customer.getBalance();

        BigDecimal newBalance;

        BigDecimal transactionAmount = deposit.getTransactionAmount();

        newBalance = currentBalance.add(transactionAmount);

        customer.setBalance(newBalance);

        deposit.setCustomer(customer);
        depositService.save(deposit);

        Customer newCustomer = customerService.save(customer);

        modelAndView.addObject("customer", newCustomer);
        modelAndView.addObject("deposit", new Deposit());

        return modelAndView;
    }
    @PostMapping("/transfer/{senderId}")
    public ModelAndView doTransfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/transfer");

        Optional<Customer> senderOptional = customerService.findById(senderId);
        Customer sender = senderOptional.get();

        BigDecimal currentSenderBalance = sender.getBalance();

        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal fees = new BigDecimal(10L);
        BigDecimal feesAmount = transferAmount.multiply(fees).divide(new BigDecimal(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (transactionAmount.compareTo(currentSenderBalance) > 0) {
            modelAndView.addObject("errors", "Số dư tài khoản không đủ thực hiện giao dịch");
        } else {
            transfer.setFees(fees.longValueExact());
            transfer.setFeesAmount(feesAmount);
            transfer.setTransactionAmount(transactionAmount);
            transfer.setSender(senderOptional.get());

            transfer.setSender(sender);
            transferService.doTransfer(transfer);
        }

        List<Customer> recipients = customerService.findByIdIsNot(senderId);

        modelAndView.addObject("sender", sender);
        modelAndView.addObject("recipients", recipients);
        modelAndView.addObject("transfer", new Transfer());


        return modelAndView;
    }


}
