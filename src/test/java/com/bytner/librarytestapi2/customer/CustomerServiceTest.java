package com.bytner.librarytestapi2.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.customer.model.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testGetAll() {
        // Given
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Warszawa")
                .build());

        expectedCustomers.add(Customer.builder()
                .id(2)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Warszawa")
                .build());
        when(customerRepository.findAllByActiveTrue()).thenReturn(expectedCustomers);

        // When
        List<Customer> actualCustomers = customerService.getAll();

        // Then
        assertNotNull(actualCustomers);
        assertEquals(expectedCustomers.size(), actualCustomers.size());
        assertEquals(expectedCustomers.get(0).getId(), actualCustomers.get(0).getId());
        assertEquals(expectedCustomers.get(1).getId(), actualCustomers.get(1).getId());
    }

    @Test
    public void testSave() {
        // Given
        Customer customer = Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Warszawa")
                .build();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer savedCustomer = customerService.save(customer);

        // Then
        assertNotNull(savedCustomer);
        assertEquals(customer.getId(), savedCustomer.getId());
        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());
        assertEquals(customer.isActive(), savedCustomer.isActive());
    }

    @Test
    public void testGetAllCustomers() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Warszawa")
                .build());
        expectedCustomers.add(Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Warszawa")
                .build());
        when(customerRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<Customer>(expectedCustomers));

        // When
        Page<Customer> actualCustomers = customerService.getAllCustomers(pageRequest);

        // Then
        assertNotNull(actualCustomers);
        assertEquals(expectedCustomers.size(), actualCustomers.getContent().size());
        assertEquals(expectedCustomers.get(0).getId(), actualCustomers.getContent().get(0).getId());
        assertEquals(expectedCustomers.get(1).getId(), actualCustomers.getContent().get(1).getId());
    }

}