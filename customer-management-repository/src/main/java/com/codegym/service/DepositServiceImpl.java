package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class DepositServiceImpl implements DepositService {
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void remove(Long id) {
        depositRepository.deleteById(id);
    }


    @Override
    public Customer deposit(Deposit deposit) {
        deposit.setId(0L);
        depositRepository.save(deposit);

        Customer customer = deposit.getCustomer();
        customerRepository.incrementBalance(customer.getId(), deposit.getTransactionAmount());
        customer.setBalance(customer.getBalance().add(deposit.getTransactionAmount()));
        return customer;
    }
}
