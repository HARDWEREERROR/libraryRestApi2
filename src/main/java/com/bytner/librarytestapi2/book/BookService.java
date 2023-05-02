package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
public class BookService {

    public final BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book blockRent(int bookId) {

        Book book = bookRepository.findWithOptimistickLockingById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Book with id={0} has not been found", bookId)));

        if (!book.isRentAvaliable()) {
            throw new OptimisticLockException("Book has been updated concurrently");
        }

        book.setRentAvaliable(false);
        return bookRepository.save(book);
    }

    @Transactional
    public Book unlockRent(int bookId) {
        Book book = bookRepository.findWithOptimistickLockingById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Book with id={0} has not been found", bookId)));

        if (book.isRentAvaliable()) {
            throw new OptimisticLockException("Book has been updated concurrently");
        }

        book.setRentAvaliable(true);
        return bookRepository.save(book);

    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
