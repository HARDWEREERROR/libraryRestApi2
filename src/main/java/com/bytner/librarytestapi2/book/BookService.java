package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    public final BookRepository bookRepository;

    public List<Book> getAll(){
        return bookRepository.findAllByActiveTrue();
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }
}
