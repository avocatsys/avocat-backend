package com.avocat.controller.customer.dto;

import com.avocat.persistence.entity.Customer;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerDto {

    private UUID id;
    private String fullName;
    private String officeName;

    private CustomerDto(Customer customer){
        this.id = customer.getId();
        this.fullName = customer.getFullName();
        this.officeName = customer.getOfficeName();
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer);
    }
}
