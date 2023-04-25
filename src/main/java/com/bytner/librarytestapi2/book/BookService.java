package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Book blockOrUnlockRent(int bookId) {
        Book book = bookRepository.findWithLockingById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Book with id={0} has not been found", bookId)));
        if (book.isRentAvaliable() == true) {
            book.setRentAvaliable(false);
        } else {
            book.setRentAvaliable(true);
        }
        return bookRepository.save(book);
    }

    public Page<Book> getAllBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }
}
