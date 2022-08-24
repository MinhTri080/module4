package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.repository.CustomerRepository;
import com.codegym.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public List<Customer> findByIdIsNot(Long id) {
        return customerRepository.findByIdIsNot(id);
    }

    @Override
    public List<CustomerDTO> findAllCustomerDTO() {
        return customerRepository.findALlCustomerDTO();
    }

    @Override
    public void incrementBalance(Long customerId, BigDecimal balance) {
        customerRepository.incrementBalance(customerId, balance);
    }

    @Override
    public void subBalance(Long customerId, BigDecimal balance) {
        customerRepository.subBalance(customerId,balance);
    }
}
