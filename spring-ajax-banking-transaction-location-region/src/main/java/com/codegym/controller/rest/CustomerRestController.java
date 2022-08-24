package com.codegym.controller.rest;

import com.codegym.model.*;
import com.codegym.model.dto.*;
import com.codegym.service.customer.CustomerService;
import com.codegym.service.deposit.DepositService;
import com.codegym.service.locationRegion.LocationRegionService;
import com.codegym.service.transfer.TransferService;
import com.codegym.service.withdraw.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    //    @Autowired
//    private LocationRegionRepository locationRegionRepository;
    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private LocationRegionService locationRegionService;

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomer() {
        List<CustomerDTO> customers = customerService.findAllCustomerDTO();
        if (customers.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CustomerDTO customerDTO) {
        customerDTO.getLocationRegion().setId(0L);
        LocationRegion locationRegion = locationRegionService.save(customerDTO.getLocationRegion().toLocationRegion());

        customerDTO.setLocationRegion(locationRegion.toLocationRegionDTO());
        customerDTO.setId(0L);
        customerDTO.setBalance(new BigDecimal(0L));

        Customer customer = customerDTO.toCustomer();

        Customer newCustomer = customerService.save(customer);

        return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> doWithdraw(@RequestBody WithdrawDTO withdrawDTO) {
        Optional<Customer> customer = customerService.findById(withdrawDTO.getCustomerId());
        Withdraw withdraw = withdrawDTO.toWithDraw(customer.get());

//        withdrawService.withdraw(withdraw);
        Customer customer1 = withdrawService.withdraw(withdraw);
//        withdrawService.save(withdraw);
//        customerService.save(customer.get());

        return new ResponseEntity<>(customer1.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> doDeposit(@RequestBody DepositDTO depositDTO) {

        Optional<Customer> customer = customerService.findById(depositDTO.getCustomerId());
        Deposit deposit = depositDTO.toDeposit(customer.get());

        Customer customer1 = depositService.deposit(deposit);

        return new ResponseEntity<>(customer1.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> doTransfer(@RequestBody TransferDTO transferDTO) {
        Optional<Customer> sender = customerService.findById(transferDTO.getSenderId());
        if (sender.isPresent()) {

            Optional<Customer> recipdent = customerService.findById(transferDTO.getRecipientId());
            Customer senderCustomer = sender.get();
            Customer recipdentCustomer = recipdent.get();

            Transfer transfer = transferDTO.toTransfer(senderCustomer, recipdentCustomer);

//            BigDecimal curentBalanceSender = senderCustomer.getBalance();
//            BigDecimal curentBalanceRecipdent = recipdentCustomer.getBalance();
            BigDecimal transferAmount = transfer.getTransferAmount();
            float fees = transferDTO.getFees();

            BigDecimal feesAmount = transferAmount.multiply(new BigDecimal(fees)).divide(new BigDecimal(100L));
            BigDecimal transactionAmount = transferAmount.add(feesAmount);

//            BigDecimal newBalanceSender = curentBalanceSender.subtract(transactionAmount);
//            BigDecimal newBalanceRecipdent = curentBalanceRecipdent.add(transferAmount);

            transfer.setFeesAmount(feesAmount);
            transfer.setTransactionAmount(transactionAmount);

            Transfer transfer1 = transferService.save(transfer);
            customerService.subBalance(senderCustomer.getId(),transactionAmount);
            customerService.incrementBalance(recipdentCustomer.getId(),transferAmount);


//            senderCustomer.setBalance(newBalanceSender);
//            customerService.save(senderCustomer);
//
//            recipdentCustomer.setBalance(newBalanceRecipdent);
//            customerService.save(recipdentCustomer);

            return new ResponseEntity<>(transfer1.toTransferDTO(),HttpStatus.OK);

        }
        return null;
    }
}
