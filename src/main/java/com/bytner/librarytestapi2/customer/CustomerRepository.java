package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByActiveTrue();
}
