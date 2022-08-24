package com.codegym.service.withdraw;

import com.codegym.model.Customer;
import com.codegym.model.Withdraw;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.WithdrawRepository;
import com.codegym.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class WithdrawServiceImpl implements WithdrawService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WithdrawRepository withdrawRepository;


    @Override
    public Iterable<Withdraw> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void remove(Long id) {
    withdrawRepository.deleteById(id);
    }

    @Override
    public Customer withdraw(Withdraw withdraw) {
        withdraw.setId(0L);
        withdrawRepository.save(withdraw);

        Customer customer = withdraw.getCustomer();
        customerRepository.subBalance(customer.getId(),withdraw.getTransactionAmount());
        customer.setBalance(customer.getBalance().subtract(withdraw.getTransactionAmount()));

        return customer;
    }
}
