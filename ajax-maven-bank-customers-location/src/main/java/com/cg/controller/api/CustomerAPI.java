package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Customer;
import com.cg.model.dto.*;
import com.cg.service.customer.ICustomerService;
import com.cg.service.locationRegion.ILocationRegionService;
import com.cg.service.transfer.ITransferService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ILocationRegionService locationRegionService;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping
    public ResponseEntity<Iterable<?>> findAll() {
        try {
            List<CustomerDTO> customerDTOS = customerService.findAllCustomerDTOByDeletedIsFalse();

            if (customerDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customerDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> showUpdateForm(@PathVariable Long id) {
        Optional<CustomerDTO> customerDTO = customerService.findCustomerDTOById(id);

        if (customerDTO.isPresent()) {
            long locationRegionId = customerDTO.get().getLocationRegion().getId();
            LocationRegionDTO locationRegionDTO = locationRegionService.getLocationRegionDTOById(locationRegionId);

            Map<String, Object> result = new HashMap<>();
            result.put("customer", customerDTO.get());
            result.put("locationRegion", locationRegionDTO);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new ResourceNotFoundException("No customer found with the Id: " + id);
        }
    }

    @GetMapping("/deposit/{id}")
    public ResponseEntity<DepositDTO> showDepositsForm(@PathVariable Long id) {

        Optional<DepositDTO> depositDTO = customerService.findDepositDTOById(id);

        if (depositDTO.isPresent()) {

            return new ResponseEntity<>(depositDTO.get(), HttpStatus.OK);

        } else {
            throw new ResourceNotFoundException("No customer found with the Id: " + id);
        }
    }

    @GetMapping("/withdraw/{id}")
    public ResponseEntity<WithdrawDTO> showWithdrawForm(@PathVariable Long id) {

        Optional<WithdrawDTO> withdrawDTO = customerService.findWithdrawDTOById(id);

        if (withdrawDTO.isPresent()) {

            return new ResponseEntity<>(withdrawDTO.get(), HttpStatus.OK);

        } else {
            throw new ResourceNotFoundException("No customer found with the Id: " + id);
        }
    }

    @GetMapping("/transfer/{id}")
    public ResponseEntity<?> showTransferForm(@PathVariable Long id) {

        Optional<TransferDTO> transferDTO = transferService.findTransferDTOById(id);

        Iterable<RecipientDTO> recipientDTOS = customerService.findAllRecipientDTOByIdWithOutSenderAndDeletedIsFalse(id);

        Map<String, Object> result = new HashMap<>();

        if (transferDTO.isPresent()) {
            result.put("transfer", transferDTO.get());
            result.put("recipients", recipientDTOS);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new ResourceNotFoundException("No customer found with the Id: " + id);
        }
    }


    @PostMapping
    public ResponseEntity<?> createCustomer(@Validated @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<CustomerDTO> optCustomer = customerService.findCustomerDTOByEmail(customerDTO.getEmail());

        if (optCustomer.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        customerDTO.setId(0);
        customerDTO.setBalance(BigDecimal.valueOf(0));

        try {
            Customer createdCustomer = customerService.create(customerDTO.toCustomer());
            customerDTO = createdCustomer.toCustomerDTO();

            return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Customer creation information is not valid, please check the information again");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateCustomer(@Validated @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<CustomerDTO> optCustomer = customerService.findCustomerDTOByEmailAndIdIsNot(customerDTO.getEmail(), customerDTO.getId());

        if (optCustomer.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        try {
            Customer updatedCustomer = customerService.update(customerDTO.toCustomer());
            customerDTO = updatedCustomer.toCustomerDTO();

            return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid customer update information");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> doDeposit(@Validated @RequestBody DepositDTO depositDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        try {
            CustomerDTO customerDTO = customerService.doDeposit(depositDTO);

            return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid deposit information");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> doWithdraw(@Validated @RequestBody WithdrawDTO withdrawDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<CustomerDTO> customer = customerService.findCustomerDTOById(withdrawDTO.getCustomerId());

        if (customer.isPresent()) {
            BigDecimal current_balance = customer.get().getBalance();
            BigDecimal transactionAmount = withdrawDTO.getTransactionAmount();

            if (current_balance.compareTo(transactionAmount) >= 0) {
                try {
                    CustomerDTO customerDTO = customerService.doWithdraw(withdrawDTO);

                    return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

                } catch (DataIntegrityViolationException e) {
                    throw new DataInputException("Invalid withdrawal information");
                }
            } else {
                throw new DataInputException("Customer's balance is not enough to make a withdrawal");
            }
        } else {
            throw new DataInputException("Invalid withdrawal customer information");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> doTransfer(@Validated @RequestBody TransferDTO transferDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (transferDTO.getSenderId().equals(transferDTO.getRecipientId())){
            throw new DataInputException("Invalid sender and receiver information");
        }

        Optional<CustomerDTO> sender = customerService.findCustomerDTOById(transferDTO.getSenderId());

        if (sender.isPresent()) {
            BigDecimal sender_balance = sender.get().getBalance();
            BigDecimal transferAmount = transferDTO.getTransferAmount();
            int fees = 10;
            BigDecimal feeAmount = transferAmount.divide(BigDecimal.valueOf(fees));
            BigDecimal transactionAmount = transferAmount.add(feeAmount);

            Optional<CustomerDTO> recipient = customerService.findCustomerDTOById(transferDTO.getRecipientId());

            if (recipient.isPresent()) {
                if (sender_balance.compareTo(transactionAmount) >= 0) {
                    try {
                        transferDTO.setFees(fees);
                        transferDTO.setFeesAmount(feeAmount);
                        transferDTO.setTransactionAmount(transactionAmount);

                        customerService.doTransfer(transferDTO, sender.get(), recipient.get());

                        CustomerDTO senderSuccess = customerService.getCustomerDTOById(transferDTO.getSenderId());
                        CustomerDTO recipientSuccess = customerService.getCustomerDTOById(transferDTO.getRecipientId());

                        Map<String, Object> result = new HashMap<>();
                        result.put("sender", senderSuccess);
                        result.put("recipient", recipientSuccess);

                        return new ResponseEntity<>(result, HttpStatus.CREATED);

                    } catch (DataIntegrityViolationException e) {
                        throw new DataInputException("Invalid transaction information");
                    }
                } else {
                    throw new DataInputException("The sender's balance is not enough to make the transfer");
                }
            } else {
                throw new DataInputException("Invalid recipient information");
            }
        } else {
            throw new DataInputException("Invalid sender information");
        }
    }

    @PostMapping("/suspend/{id}")
    public ResponseEntity<?> doSuspend(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            try {
                customer.get().setDeleted(true);
                customerService.save(customer.get());

                return new ResponseEntity<>(HttpStatus.CREATED);

            } catch (DataIntegrityViolationException e) {
                throw new DataInputException("Invalid suspension information");
            }
        } else {
            throw new DataInputException("Invalid customer information");
        }
    }
}
