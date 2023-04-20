package com.bytner.librarytestapi2.bookuser.model;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.user.model.User;
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
public class BookUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate localDate;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return localDate + " " + book.getTitle() + " " + user.getFirstName();
    }
}
