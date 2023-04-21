package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
