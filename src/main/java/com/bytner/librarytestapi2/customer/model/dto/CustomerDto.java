package com.bytner.librarytestapi2.customer.model.dto;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.customer.model.Customer;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CustomerDto {

    private int id;
    private String firstName;
    private String lastName;
    private String adress;
    private int numOfRentSet;

    public static CustomerDto fromEntity(Customer customer) {
        Set<Rent> rents = customer.getRentSet();
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .adress(customer.getAdress())
                .numOfRentSet(rents == null ? 0 : rents.size())
                .build();
    }
}
