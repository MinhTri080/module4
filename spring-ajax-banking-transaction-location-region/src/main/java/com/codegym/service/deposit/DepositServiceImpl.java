package com.codegym.service.deposit;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DepositRepository depositRepository;

    @Override
    public Customer deposit(Deposit deposit) {
        Long id = deposit.getCustomer().getId();
        depositRepository.save(deposit);

        Customer customer = deposit.getCustomer();
        customerRepository.incrementBalance(id,deposit.getTransactionAmount());

//        Customer customer1 = customerRepository.findById(deposit.getCustomer().getId()).get();
//        customer1.setBalance(customer1.getBalance().add(deposit.getTransactionAmount()));
//
//        Customer customer2 = customerRepository.save(customer1);
        return customer;
    }

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

    }
}
