package com.bytner.librarytestapi2.bookuser;

import com.bytner.librarytestapi2.bookuser.model.BookCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCustomerRepository extends JpaRepository<BookCustomer, Integer> {
}
