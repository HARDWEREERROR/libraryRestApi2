package com.bytner.librarytestapi2.rent;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.RentRepository;
import com.bytner.librarytestapi2.bookuser.RentService;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.RentStatus;
import com.bytner.librarytestapi2.customer.CustomerRepository;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class RentServiceTest {

    @MockBean
    private RentRepository rentRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CustomerRepository customerRepository;

    private RentService rentService;

    private final Rent mockRent = new Rent();
    private final Book mockBook = new Book();
    private final Customer mockCustomer = new Customer();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        rentService = new RentService(rentRepository, bookRepository, customerRepository);

        mockRent.setId(1);
        mockRent.setLocalDateFrom(LocalDate.now().plusDays(1));
        mockRent.setLocalDateTo(LocalDate.now().plusDays(7));
        mockRent.setRentStatus(RentStatus.BOOKED);

        mockBook.setId(1);
        mockBook.setTitle("Mock Book");
        mockBook.setAuthor("Mock Author");
        mockBook.setStatus(BookStatus.IN_STORAGE);
        mockBook.setRentAvaliable(true);

        mockCustomer.setId(1);
        mockCustomer.setFirstName("Mock Customer");
        mockCustomer.setAdress("mock@example.com");
        mockCustomer.setBookSet(new ArrayList<>());
    }

    @Test
    @DisplayName("Test getAll() method")
    public void testGetAll() {
        List<Rent> rentList = new ArrayList<>();
        rentList.add(mockRent);
        when(rentRepository.findAll()).thenReturn(rentList);

        List<Rent> result = rentService.getAll();

        Assertions.assertEquals(rentList, result);
    }

    @Test
    @DisplayName("Test saveReservation() method with valid input")
    public void testSaveReservationWithValidInput() {
        when(bookRepository.findWithLockingById(anyInt())).thenReturn(Optional.of(mockBook));
        when(customerRepository.findWithLockingById(anyInt())).thenReturn(Optional.of(mockCustomer));
        when(rentRepository.save(any(Rent.class))).thenAnswer((Answer<Rent>) invocation -> {
            Rent rent = invocation.getArgument(0);
            rent.setId(1);
            return rent;
        });

//    @Test
//    void testSaveReservationWithInvalidDates() {
//        int bookId = 1;
//        int customerId = 2;
//        Rent rent = new Rent();
//        rent.setLocalDateFrom(LocalDate.now().minusDays(1));
//        rent.setLocalDateTo(LocalDate.now().minusDays(5));
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            rentService.saveReservation(rent, bookId, customerId);
//        });
//
//        Mockito.verify(bookRepository, Mockito.never()).findWithLockingById(Mockito.anyInt());
//        Mockito.verify(customerRepository, Mockito.never()).findWithLockingById(Mockito.anyInt());
//        Mockito.verify(rentRepository, Mockito.never()).findByBookAndRentalDateRange(Mockito.any(), Mockito.any(), Mockito.any());
//        Mockito.verify(rentRepository, Mockito.never()).save(Mockito.any());
//    }

    }
}