package com.bytner.librarytestapi2.book.model;

import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.common.BookStatus;
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
import jakarta.persistence.Version;
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

    private boolean rentAvaliable = true;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "book")
    private Set<Rent> bookUserSet;

    @Version
    private long version;

    @Override
    public String toString() {
        return title + " " + author;
    }
}



