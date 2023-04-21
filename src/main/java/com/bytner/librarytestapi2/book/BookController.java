package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.book.model.command.CreateBookCommand;
import com.bytner.librarytestapi2.book.model.command.UpdateBookCommand;
import com.bytner.librarytestapi2.book.model.dto.BookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/LibraryRestAPI2/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll().stream()
                .map(BookDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus
    public BookDto createBook(@RequestBody @Valid CreateBookCommand createBookCommand) {
        Book toSave = createBookCommand.toEntity();
        Book saved = bookService.save(toSave);
        return BookDto.fromEntity(saved);
    }

    @PatchMapping("/{id}")
    public BookDto blockOrUnlockRent(@PathVariable int id) {
        Book update = bookService.blockOrUnlockRent(id);
        return BookDto.fromEntity(update);
    }
}
