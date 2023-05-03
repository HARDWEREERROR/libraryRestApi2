package com.bytner.librarytestapi2.rent;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.RentRepository;
import com.bytner.librarytestapi2.bookuser.RentService;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.bookuser.model.commmand.CreateRentCommand;
import com.bytner.librarytestapi2.customer.CustomerRepository;
import com.bytner.librarytestapi2.customer.model.Customer;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.RentStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    private RentService rentService;

    @Mock
    private RentRepository rentRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentService = new RentService(rentRepository, bookRepository, customerRepository);
    }

    @Test
    void shouldReturnAllRents() {
        // given
        List<Rent> rents = new ArrayList<>();
        Rent rent1 = new Rent();
        rent1.setId(1);
        Rent rent2 = new Rent();
        rent2.setId(2);
        rents.add(rent1);
        rents.add(rent2);
        when(rentRepository.findAll()).thenReturn(rents);

        // when
        List<Rent> result = rentService.getAll();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(rent1));
        assertTrue(result.contains(rent2));
    }

    @Test
    void shouldThrowExceptionWhenSavingReservationWithInvalidDates() {
        // given
        CreateRentCommand command = new CreateRentCommand();
        command.setLocalDateFrom(LocalDate.of(2022, 1, 1));
        command.setLocalDateTo(LocalDate.of(2021, 12, 31));
        // when, then
        assertThrows(IllegalArgumentException.class, () -> rentService.saveReservation(command));
    }

    @Test
    public void shouldSaveReservation() {
        // given
        CreateRentCommand command = new CreateRentCommand();
        command.setBookId(1);
        command.setCustomerId(1);
        command.setLocalDateFrom(LocalDate.now().plusDays(1));
        command.setLocalDateTo(LocalDate.now().plusDays(5));
        Rent rent = command.toEntity();

        Book book = new Book();
        book.setId(1);
        book.setRentAvaliable(true);

        Customer customer = new Customer();
        customer.setId(1);

        when(bookRepository.findWithLockingById(1)).thenReturn(Optional.of(book));
        when(customerRepository.findWithLockingById(1)).thenReturn(Optional.of(customer));
        when(rentRepository.findByBookAndRentalDateRange(any(Book.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        // when
        Rent result = rentService.saveReservation(command);

        // then
        assertEquals(rent, result);
        assertEquals(RentStatus.BOOKED, rent.getRentStatus());
        verify(bookRepository).findWithLockingById(1);
        verify(customerRepository).findWithLockingById(1);
        verify(rentRepository).findByBookAndRentalDateRange(any(Book.class), any(LocalDate.class), any(LocalDate.class));
        verify(rentRepository).save(any(Rent.class));
    }

}