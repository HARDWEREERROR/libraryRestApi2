package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    public final BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAllByActiveTrue();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book blockOrUnlockRent(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Book with id={0} has not been found", bookId)));
        if (book.isRentAvaliable() == true) {
            book.setRentAvaliable(false);
        } else {
            book.setRentAvaliable(true);
        }
        return bookRepository.save(book);
    }
}
