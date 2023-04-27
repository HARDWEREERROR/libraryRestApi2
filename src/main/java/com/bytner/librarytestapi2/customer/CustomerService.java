package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAllByActiveTrue();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Page<Customer> getAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }

    @Transactional
    public List<Book> getCustomerHistory(int customerId) {
        Customer customer = customerRepository.findWithLockingById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Customer with id={0} has not been found", customerId)));

        List<Book> historySet = customer.getBookSet();
        return historySet;
    }
}
