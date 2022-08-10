package com.codegym.service;

import com.codegym.model.Customer;

import java.time.temporal.ChronoUnit;

public interface ICustomerService extends IGeneralService<Customer>{
    boolean insertWithStoredProcedure(Customer customer);
}
