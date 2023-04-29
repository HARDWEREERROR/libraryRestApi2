package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
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
    public Book blockRent(int bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Book with id={0} has not been found", bookId)));

        if (!book.isRentAvaliable()) {
            throw new IllegalArgumentException("Book is already blocked");
        }
        book.setRentAvaliable(false);
        try {
            return bookRepository.save(book);
        } catch (OptimisticLockException exception) {
            throw new IllegalStateException("Book has been updated concurrently", exception);
        }
    }


    @Transactional
    public Book unlockRent(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Book with id={0} has not been found", bookId)));

        if (book.isRentAvaliable()) {
            throw new IllegalArgumentException("Book is already unblocked");
        }

        book.setRentAvaliable(true);
        bookRepository.saveAndFlush(book); // save and flush to perform optimistic lock check

        return book;
    }


    public Page<Book> getAllBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }
}
