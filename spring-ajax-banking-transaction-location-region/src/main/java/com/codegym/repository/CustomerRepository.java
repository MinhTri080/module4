package com.codegym.repository;

import com.codegym.model.Customer;
import com.codegym.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByIdIsNot(Long id);
    @Modifying
    @Query("UPDATE Customer AS c " +
            "SET c.balance = c.balance + :balance " +
            "WHERE c.id = :customerId")
    void incrementBalance(@Param("customerId") Long customerId, @Param("balance") BigDecimal balance);


    @Modifying
    @Query("SELECT NEW com.codegym.model.dto.CustomerDTO("+
                    "c.id, "+
                    "c.fullName, "+
                    "c.email, "+
                    "c.phone, " +
                    "c.balance, "+
                    "c.locationRegion " +
                    ") FROM Customer as c")
    List<CustomerDTO> findALlCustomerDTO();

    @Modifying
    @Query("UPDATE Customer AS c " +
            "SET c.balance = c.balance - :balance " +
            "WHERE c.id = :customerId")
    void subBalance(@Param("customerId") Long customerId, @Param("balance") BigDecimal balance);

//    @Modifying
//    @Query("UPDATE Customer AS c " +
//            "SET c.balance = c.balance + :balance " +
//            "WHERE c.id = :customerId")
//    void addbBalance(@Param("customerId") Long customerId, @Param("balance") BigDecimal balance);
}

