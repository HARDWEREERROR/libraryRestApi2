package com.bytner.librarytestapi2.bookuser;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Integer> {

    @Query("SELECT r FROM Rent r WHERE r.book = :book AND r.localDateFrom <= :newRentalDateEnd AND r.localDateTo >= :newRentalDateStart")
    List<Rent> findByBookAndRentalDateRange(Book book, LocalDate newRentalDateStart, LocalDate newRentalDateEnd);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Rent> findWithLockingById(int id);

}
