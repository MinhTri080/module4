package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService extends IGeneralService<Customer> {
//    void remove(Long id);

    List<Customer> findByIdIsNot(Long id);

    List<CustomerDTO> findAllCustomerDTO();
    void incrementBalance(Long customerId, BigDecimal balance);
    void subBalance(Long customerId,BigDecimal balance);
}
