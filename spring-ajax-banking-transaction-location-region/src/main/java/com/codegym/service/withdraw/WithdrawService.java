package com.codegym.service.withdraw;

import com.codegym.model.Customer;
import com.codegym.model.Withdraw;
import com.codegym.service.IGeneralService;

public interface WithdrawService extends IGeneralService<Withdraw> {
    Customer withdraw(Withdraw withdraw) ;
}
