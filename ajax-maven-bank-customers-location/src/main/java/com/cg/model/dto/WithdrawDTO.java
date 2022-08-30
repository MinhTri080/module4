package com.cg.model.dto;

import com.cg.model.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawDTO implements Validator {
    private long customerId;
    private String fullName;
    private BigDecimal balance;

    @NotNull(message = "The transaction amount is required")
    @DecimalMin(value = "50", message = "Transaction Amount must be greater than or equal to 50")
    @DecimalMax(value = "10000000000", message = "Transaction Amount must be less than or equal to 10.000.000.000")
    private BigDecimal transactionAmount;

    public WithdrawDTO(long customerId, String fullName, BigDecimal balance) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.balance = balance;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WithdrawDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        WithdrawDTO withdrawDTO = (WithdrawDTO) target;
        BigDecimal transactionAmount = withdrawDTO.getTransactionAmount();

        if (transactionAmount != null) {
            if (transactionAmount.toString().length() > 9){
                errors.rejectValue("transactionAmount", "transactionAmount.length");
            }

            if (!transactionAmount.toString().matches("(^$|[0-9]*$)")){
                errors.rejectValue("transactionAmount", "transactionAmount.matches");
            }
        }else {
            errors.rejectValue("transactionAmount", "transactionAmount.null");
        }
    }

    public Withdraw toWithdraw(CustomerDTO customerDTO) {
        return new Withdraw()
            .setCustomer(customerDTO.toCustomer())
            .setTransactionAmount(transactionAmount);
    }
}
