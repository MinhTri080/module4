package com.codegym.service;

import com.codegym.model.Customer;

import java.util.List;

public interface CustomerService extends IGeneralService<Customer>{
//    void remove(Long id);

    List<Customer> findByIdIsNot(Long id);
}
