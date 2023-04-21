package com.bytner.librarytestapi2.customer.model;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.BookCustomer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String adress;

    private boolean active = true;

    @OneToMany()
    private Set<Book> bookSet;

    @OneToMany()
    private Set<BookCustomer> bookUserSet;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
