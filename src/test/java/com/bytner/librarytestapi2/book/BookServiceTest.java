package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.common.TypeOfBook;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    private final int bookId = 1;
    private final Book testBook = Book.builder()
            .title("Siema")
            .author("Hubert Bytner")
            .typeOfBook(TypeOfBook.ADVENTURE)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    void getAll_ReturnsListOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        when(bookRepository.findAllByActiveTrue()).thenReturn(books);

        List<Book> result = bookService.getAll();

        assertEquals(books, result);
    }

    @Test
    void save_ReturnsSavedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book result = bookService.save(testBook);

        assertEquals(testBook, result);
    }

//    @Test
//    void blockOrUnlockRent_BookExists_UpdatesAndReturnsBook() {
//        when(bookRepository.findWithLockingById(bookId)).thenReturn(Optional.of(testBook));
//        when(bookRepository.save(any(Book.class))).thenReturn(testBook);
//
//        Book result = bookService.blockOrUnlockRent(bookId);
//
//        verify(bookRepository, times(1)).findWithLockingById(bookId);
//        verify(bookRepository, times(1)).save(any(Book.class));
//        assertFalse(result.isRentAvaliable());
//    }

    @Test
    void blockOrUnlockRent_BookDoesNotExist_ThrowsEntityNotFoundException() {
        when(bookRepository.findWithLockingById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.blockOrUnlockRent(bookId));
    }

    @Test
    void getAllBooks_ReturnsPageOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        Page<Book> bookPage = new PageImpl<>(books);
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        Page<Book> result = bookService.getAllBooks(pageRequest);

        assertEquals(bookPage, result);
    }
}
