package com.bytner.librarytestapi2.bookuser;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.customer.CustomerRepository;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    private final BookRepository bookRepository;

    private final CustomerRepository customerRepository;

    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    public Rent save(Rent rent, int bookId, int customerId) {
        LocalDate localDateFrom = rent.getLocalDateFrom();
        LocalDate localDateTo = rent.getLocalDateTo();

        if (rent.getLocalDateFrom().isBefore(LocalDate.now()) || rent.getLocalDateTo().isBefore(rent.getLocalDateFrom())) {
            throw new IllegalArgumentException("Rent cannot start in the past & end of the rent cannot be before the start");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Book with id={0} has not been found", bookId)));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Customer with id={0} has not been found", customerId)));

        List<Rent> overlappingRent = rentRepository.findByBookAndRentalDateRange(book, localDateFrom, localDateTo);

        if (!overlappingRent.isEmpty()) {
            throw new IllegalArgumentException("term is not available.");
        }


        rent.setBook(book);
        rent.setCustomer(customer);
        return rentRepository.save(rent);
    }


}
