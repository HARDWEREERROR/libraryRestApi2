package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByActiveTrue();

}
