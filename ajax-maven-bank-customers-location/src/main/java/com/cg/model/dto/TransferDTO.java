package com.cg.model.dto;

import com.cg.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    private Long senderId;
    private String senderName;
    private String email;

    @NotNull(message = "The recipient is required")
    private Long recipientId;

    private BigDecimal balance;

    @NotNull(message = "The transfer amount is required")
    @DecimalMin(value = "50", message = "Transaction Amount must be greater than or equal to 50")
    @DecimalMax(value = "10000000000", message = "Transaction Amount must be less than or equal to 10.000.000.000")
    private BigDecimal transferAmount;

    private int fees;
    private BigDecimal feesAmount;
    private BigDecimal transactionAmount;


    public TransferDTO(long id, String fullName, String email, BigDecimal balance) {
        this.senderId = id;
        this.senderName = fullName;
        this.email = email;
        this.balance = balance;
    }

    public Transfer toTransfer(CustomerDTO sender, CustomerDTO recipient) {
        return new Transfer()
                .setSender(sender.toCustomer())
                .setRecipient(recipient.toCustomer())
                .setTransferAmount(transferAmount)
                .setFees(fees)
                .setFeesAmount(feesAmount)
                .setTransactionAmount(transactionAmount);
    }

}
