package com.bytner.librarytestapi2.book.model.command;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.common.TypeOfBook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateBookCommand {


    @NotBlank
    @Pattern(regexp = "([A-Z]|[0-9]).{1,30}", message = "author has to match the pattern {regexp}")
    private String title;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]{1,19} [A-Z][a-z]{1,29}", message = "author has to match the pattern {regexp}")
    private String author;

    @NotNull
    private TypeOfBook typeOfBook;


    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .typeOfBook(typeOfBook)
                .rentAvaliable(true)
                .active(true)
                .build();
    }
}
