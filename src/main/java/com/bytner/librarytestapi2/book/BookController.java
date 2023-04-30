package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.book.model.command.CreateBookCommand;
import com.bytner.librarytestapi2.book.model.dto.BookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/books")
    public Page<Book> getBooks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookService.getBooks(pageable);
    }




//    @GetMapping("/books")
//    public List<BookDto> getAllBooks(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "3") int size, @RequestParam(name = "sort", defaultValue = "id") String sortField, @RequestParam(name = "direction", defaultValue = "asc") String sortDirection) {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
//        PageRequest pageRequest = PageRequest.of(page, size, sort);
//        return bookService.getAllBooks(pageRequest).stream()
//                .map(BookDto::fromEntity)
//                .toList();
//    }
}
