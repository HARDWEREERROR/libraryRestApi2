package com.bytner.librarytestapi2.book.model.dto;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.common.TypeOfBook;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDto {

    private int id;
    private String title;
    private String author;
    private TypeOfBook typeOfBook;
    private int userID;
    private int numOfBookCustomerList;

    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .typeOfBook(book.getTypeOfBook())
                .userID(book.getUser() == null ? 0 : book.getUser().getId())
                .numOfBookCustomerList(book.getBookUserSet() == null ? 0 : book.getBookUserSet().size())
                .build();
    }
}
