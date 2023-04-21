package com.bytner.librarytestapi2.customer.model.dto;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.BookCustomer;
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
    private String addres;
    private int numOfBookSet;
    private int numOfBookCustomerSet;

    public static CustomerDto fromEntity(Customer customer) {
        Set<Book> books = customer.getBookSet();
        Set<BookCustomer> bookCustomers = customer.getBookUserSet();
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .addres(customer.getAdress())
                .numOfBookSet(books == null ? 0 : books.size())
                .numOfBookCustomerSet(bookCustomers == null ? 0 : bookCustomers.size())
                .build();
    }
}
