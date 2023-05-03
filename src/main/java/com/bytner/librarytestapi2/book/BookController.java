package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.book.model.command.CreateBookCommand;
import com.bytner.librarytestapi2.book.model.dto.BookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/LibraryRestAPI2/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus
    public BookDto createBook(@RequestBody @Valid CreateBookCommand createBookCommand) {
        Book toSave = createBookCommand.toEntity();
        Book saved = bookService.save(toSave);
        return BookDto.fromEntity(saved);
    }

    @PatchMapping("/{id}/block")
    public BookDto blockRent(@PathVariable int id) {
        Book update = bookService.blockRent(id);
        return BookDto.fromEntity(update);
    }

    @PatchMapping("/{id}/unlock")
    public BookDto unblock(@PathVariable int id) {
        Book update = bookService.unlockRent(id);
        return BookDto.fromEntity(update);
    }

    @GetMapping()
    public Page<BookDto> getBooks(Pageable pageable) {
        return bookService.getBooks(pageable)
                .map(BookDto::fromEntity);
    }
}
