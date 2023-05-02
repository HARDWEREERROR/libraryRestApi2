package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    public void save_shouldReturnSavedBook() {
        // Given
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test book");
        book.setAuthor("Test author");

        when(bookRepository.save(any())).thenReturn(book);

        // When
        Book result = bookService.save(book);

        // Then
        verify(bookRepository, times(1)).save(book);
        Assertions.assertEquals(book, result);
    }

    @Test
    public void blockRent_shouldBlockRentForGivenBook() {
        // Given
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test book");
        book.setAuthor("Test author");
        book.setRentAvaliable(true);

        when(bookRepository.findWithOptimistickLockingById(anyInt())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        // When
        Book result = bookService.blockRent(1);

        // Then
        verify(bookRepository, times(1)).findWithOptimistickLockingById(1);
        verify(bookRepository, times(1)).save(book);
        Assertions.assertFalse(result.isRentAvaliable());
    }

    @Test
    public void blockRent_shouldThrowEntityNotFoundExceptionWhenBookIsNotFound() {
        // Given
        when(bookRepository.findWithOptimistickLockingById(anyInt())).thenReturn(Optional.empty());

        // When, Then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            bookService.blockRent(1);
        });
    }

    @Test
    public void blockRent_shouldThrowOptimisticLockExceptionWhenBookIsAlreadyRented() {
        // Given
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test book");
        book.setAuthor("Test author");
        book.setRentAvaliable(false);

        when(bookRepository.findWithOptimistickLockingById(anyInt())).thenReturn(Optional.of(book));

        // When, Then
        Assertions.assertThrows(OptimisticLockException.class, () -> {
            bookService.blockRent(1);
        });
    }
}

