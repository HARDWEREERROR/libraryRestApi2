package com.bytner.librarytestapi2.book.model;

import com.bytner.librarytestapi2.bookuser.model.BookUser;
import com.bytner.librarytestapi2.common.TypeOfBook;
import com.bytner.librarytestapi2.user.model.User;
import jakarta.persistence.Entity;
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

    private TypeOfBook typeOfBook;

    private boolean active = true;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "book")
    private Set<BookUser> bookUserSet;

    @Override
    public String toString() {
        return title + " " + author;
    }
}



