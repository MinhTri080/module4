package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;

import java.time.temporal.ChronoUnit;

public interface ICustomerService extends IGeneralService<Customer>{
//    boolean insertWithStoredProcedure(Customer customer);
Iterable<Customer> findAllByProvince(Province province);

}
