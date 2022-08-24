package com.codegym.controller;

import com.codegym.model.dto.TransferHistoryDTO;
import com.codegym.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @GetMapping
    public ModelAndView showListTransferPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/transfer/list");

//        List<Transfer> transfers = transferService.findAll();

        List<TransferHistoryDTO> transfers = transferService.findAllHistories();

        modelAndView.addObject("transfers", transfers);

        return modelAndView;
    }
}
