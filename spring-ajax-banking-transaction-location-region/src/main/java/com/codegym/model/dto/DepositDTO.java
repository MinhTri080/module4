package com.codegym.model.dto;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepositDTO {
    private Long id;
    private Long customerId;
//    private String fullName;
//    private BigDecimal balance;
    private BigDecimal transactionAmount;



    public Deposit toDeposit(Customer customer){
        return new Deposit()
                .setId(id)
                .setCustomer(customer)
                .setTransactionAmount(transactionAmount);
    }
}
