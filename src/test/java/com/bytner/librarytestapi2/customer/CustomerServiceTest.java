package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void save_shouldSaveCustomer() {
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .adress("Street 123")
                .active(true)
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.save(customer);

        assertEquals(customer, savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomers_shouldReturnCustomersPage() {
        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder().id(1).build());
        customers.add(Customer.builder().id(2).build());
        Page<Customer> page = new PageImpl<>(customers);
        Pageable pageable = Pageable.unpaged();

        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<Customer> resultPage = customerService.getCustomers(pageable);

        assertEquals(page, resultPage);
        verify(customerRepository, times(1)).findAll(pageable);
    }

    @Test
    void getCustomerHistory_shouldReturnCustomerHistory() {
        int customerId = 1;
        Customer customer = Customer.builder()
                .id(customerId)
                .bookSet(new ArrayList<>())
                .build();
        Book book1 = Book.builder().id(1).title("Book 1").build();
        Book book2 = Book.builder().id(2).title("Book 2").build();
        customer.getBookSet().add(book1);
        customer.getBookSet().add(book2);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        List<Book> customerHistory = customerService.getCustomerHistory(customerId);

        assertEquals(2, customerHistory.size());
        assertTrue(customerHistory.contains(book1));
        assertTrue(customerHistory.contains(book2));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void getCustomerHistory_shouldThrowEntityNotFoundExceptionWhenCustomerNotFound() {
        int customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerHistory(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }
}
