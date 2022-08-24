package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Transfer;
import com.codegym.model.dto.TransferHistoryDTO;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class TransferServiceImpl implements TransferService{

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public Object save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<TransferHistoryDTO> findAllHistories() {
        return transferRepository.findAllHistories();
    }

    @Override
    public void doTransfer(Transfer transfer) {

        transferRepository.save(transfer);

        Customer sender = transfer.getSender();
        BigDecimal senderBalance = sender.getBalance().subtract(transfer.getTransactionAmount());
        sender.setBalance(senderBalance);
        customerRepository.save(sender);

        Customer recipient = transfer.getRecipient();
        BigDecimal recipientBalance = recipient.getBalance().add(transfer.getTransferAmount());
        recipient.setBalance(recipientBalance);
        customerRepository.save(recipient);
    }
}
