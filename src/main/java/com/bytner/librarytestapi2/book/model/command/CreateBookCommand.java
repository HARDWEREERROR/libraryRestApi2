package com.bytner.librarytestapi2.book.model.command;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.common.TypeOfBook;

import lombok.Data;

@Data
public class CreateBookCommand {


    private String title;


//    @Pattern(regexp = "[A-Z][a-z]{1,19} [A-Z][a-z]{1,29}", message = "author has to match the pattern {regexp}")
    private String author;


    private TypeOfBook typeOfBook;


    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .typeOfBook(typeOfBook)
                .active(true)
                .build();
    }
}
