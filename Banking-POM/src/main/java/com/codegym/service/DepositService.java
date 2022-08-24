package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;

public interface DepositService extends IGeneralService<Deposit>{
    Customer deposit(Deposit deposit);
}
