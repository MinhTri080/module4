package com.codegym.model.dto;

import com.codegym.model.Customer;
import com.codegym.model.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Accessors(chain = true)
public class WithdrawDTO {
    private Long id;
    private Long customerId;
    private BigDecimal transactionAmount;

    public Withdraw toWithDraw(Customer customer){
        return new Withdraw()
                .setId(id)
                .setCustomer(customer)
                .setTransactionAmount(transactionAmount);
    }
}
