package com.codegym.model.dto;

import com.codegym.model.Customer;
import com.codegym.model.LocationRegion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private BigDecimal balance;
    private LocationRegionDTO locationRegion;

    public CustomerDTO(Long id, String fullName, String phone, String email, BigDecimal balance, LocationRegion locationRegion) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.balance = balance;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }
    public Customer toCustomer(){
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone)
                .setEmail(email)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}
