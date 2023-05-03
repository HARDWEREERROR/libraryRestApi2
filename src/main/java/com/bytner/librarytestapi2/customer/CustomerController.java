package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.book.model.dto.BookDto;
import com.bytner.librarytestapi2.customer.model.Customer;
import com.bytner.librarytestapi2.customer.model.command.CreateCustomerCommand;
import com.bytner.librarytestapi2.customer.model.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/LibraryRestAPI2/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
        Customer toSave = createCustomerCommand.toEntity();
        Customer saved = customerService.save(toSave);
        return CustomerDto.fromEntity(saved);
    }

    @GetMapping()
    public Page<CustomerDto> getCustomers(Pageable pageable) {
        return customerService.getCustomers(pageable)
                .map(CustomerDto::fromEntity);
    }

    @GetMapping("/{id}/books")
    public List<BookDto> getCustomerHistory(@PathVariable int id) {
        return customerService.getCustomerHistory(id).stream()
                .map(BookDto::fromEntity)
                .toList();
    }
}
