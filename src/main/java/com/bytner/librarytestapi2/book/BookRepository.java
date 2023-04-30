package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NamedQuery;
import org.hibernate.LockMode;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

import static java.nio.file.AccessMode.WRITE;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByActiveTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Book> findWithLockingById(int id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Book> findWithOptimistickLockingById(int bookId);
}
