package com.bytner.librarytestapi2.bookuser;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.bookuser.model.commmand.CreateRentCommand;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.RentStatus;
import com.bytner.librarytestapi2.customer.CustomerRepository;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    Rent toSave = createBookCustomerCommand.toEntity();
//    Rent saved = rentService.saveReservation(toSave, createBookCustomerCommand.getBookId(), createBookCustomerCommand.getCustomerId());

    //    zobacz swoje pulapke myslowa
//    robisz command.toEntity, a w entity nie ma bookId itp.
//    i teraz do serwisu musisz przekazac calosc, encje i dwa idki, a jak tych idkow bylo by 10?
//    nie lepiej przekazywac commanda do serwisu i w serwisie robic wsio co trzeba?
    @Transactional
    public Rent saveReservation(CreateRentCommand createRentCommand) { // czy mogę po prostu brać id z rent to znaczy: zamiast bookId zrobic rent.getBook.getId?????????
        LocalDate localDateFrom = createRentCommand.getLocalDateFrom();
        LocalDate localDateTo = createRentCommand.getLocalDateTo();

        if (createRentCommand.getLocalDateFrom().isBefore(LocalDate.now()) || createRentCommand.getLocalDateTo().isBefore(createRentCommand.getLocalDateFrom())) {
            throw new IllegalArgumentException("Rent cannot start in the past & end of the rent cannot be before the start");
        }

        Book book = bookRepository.findWithLockingById(createRentCommand.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Book with id={0} has not been found", createRentCommand.getBookId())));

        if (book.isRentAvaliable() == false) {
            throw new IllegalArgumentException("Book is not for Rent right now.");
        }

        Customer customer = customerRepository.findWithLockingById(createRentCommand.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Customer with id={0} has not been found", createRentCommand.getCustomerId())));

        List<Rent> overlappingRent = rentRepository.findByBookAndRentalDateRange(book, localDateFrom, localDateTo);

        if (overlappingRent.size() > 0) {
            throw new IllegalArgumentException("term is not available.");
        }

        Rent rent = createRentCommand.toEntity();
        rent.setBook(book);
        rent.setCustomer(customer);
        return rentRepository.save(rent);
    }


    @Transactional
    public void deleteById(int rentId) {
        Rent rent = rentRepository.findWithLockingById(rentId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Rent with id={0} has not been found", rentId)));

        if (rent.getRentStatus().equals(RentStatus.RENTAL_STARTED) || rent.getRentStatus().equals(RentStatus.RENTAL_ENDED)) {
            throw new IllegalArgumentException(MessageFormat.format("You cannot cancel this Rental because it already has started or ended. RentId: ", rentId));
        }
        rentRepository.deleteById(rentId);
    }

    @Transactional
    public Rent startReservation(int rentId) {
        Rent rent = rentRepository.findWithLockingById(rentId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Rent with id={0} has not been found", rentId)));

        if (rent.getLocalDateFrom().isAfter(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException(MessageFormat.format("Its to soon to start this reservation", rentId));
        }

        if (rent.getBook().isRentAvaliable() == false) {
            throw new IllegalArgumentException("Book is not for Rent right now.");
        }

        if (rent.getRentStatus().equals(RentStatus.RENTAL_STARTED)) {
            throw new IllegalArgumentException(MessageFormat.format("The Rental has already started", rentId));
        }

        if (rent.getRentStatus().equals(RentStatus.RENTAL_ENDED)) {
            throw new IllegalArgumentException(MessageFormat.format("The Rental has already ended", rentId));
        }

        if (rent.getBook().getCustomer() != null) {
            throw new IllegalArgumentException(MessageFormat.format("Book is still rented: ", rent.getBook().getId()));
        }

        rent.setRentStatus(RentStatus.RENTAL_STARTED);
        rent.getBook().setCustomer(rent.getCustomer());
        rent.getCustomer().getBookSet().add(rent.getBook());
        rent.getBook().setStatus(BookStatus.ON_RENTAL);
        return rent;
    }


    @Transactional
    public Rent endReservation(int rentId) {
        Rent rent = rentRepository.findWithLockingById(rentId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Rent with id={0} has not been found", rentId)));

        if (!rent.getRentStatus().equals(RentStatus.RENTAL_STARTED)) {
            throw new IllegalArgumentException(MessageFormat.format("This Reservation is NOT ACTIVE", rentId));
        }

        rent.setRentStatus(RentStatus.RENTAL_ENDED);
        rent.getBook().setCustomer(null);
        rent.getBook().setStatus(BookStatus.IN_STORAGE);
        return rent;
    }
}
