package com.bytner.librarytestapi2.book.model;

import com.bytner.librarytestapi2.bookuser.model.BookCustomer;
import com.bytner.librarytestapi2.common.TypeOfBook;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String author;

    @Enumerated(EnumType.STRING)
    private TypeOfBook typeOfBook;

    private boolean active = true;

    @ManyToOne
    private Customer user;

    @OneToMany(mappedBy = "book")
    private Set<BookCustomer> bookUserSet;

    @Override
    public String toString() {
        return title + " " + author;
    }
}



