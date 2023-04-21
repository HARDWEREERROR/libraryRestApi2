package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.customer.model.Customer;
import com.bytner.librarytestapi2.customer.model.command.CreateCustomerCommand;
import com.bytner.librarytestapi2.customer.model.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/LibraryRestAPI2/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll().stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto CreateCustomer(@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
        Customer toSave = createCustomerCommand.toEntity();
        Customer saved = customerService.save(toSave);
        return CustomerDto.fromEntity(saved);
    }
}
