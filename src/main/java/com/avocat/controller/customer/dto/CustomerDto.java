package com.avocat.controller.customer.dto;

import com.avocat.persistence.entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CustomerDto {

    @JsonProperty("customerId")
    private UUID id;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("officeName")
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
