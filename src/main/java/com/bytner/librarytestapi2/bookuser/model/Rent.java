package com.bytner.librarytestapi2.bookuser.model;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate localDateFrom;

    private LocalDate localDateTo;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Customer customer;

    @Override
    public String toString() {
        return "From: " + " " + localDateFrom + " To: " + localDateTo + " " + book.getTitle() + " " + customer.getFirstName();
    }
}
