package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.book.model.dto.BookDto;
import com.bytner.librarytestapi2.customer.model.Customer;
import com.bytner.librarytestapi2.customer.model.command.CreateCustomerCommand;
import com.bytner.librarytestapi2.customer.model.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll().stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
        Customer toSave = createCustomerCommand.toEntity();
        Customer saved = customerService.save(toSave);
        return CustomerDto.fromEntity(saved);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "3") int size, @RequestParam(name = "sort", defaultValue = "id") String sortField, @RequestParam(name = "direction", defaultValue = "asc") String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return customerService.getAllCustomers(pageRequest).stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }

    @GetMapping("/{id}/books")
    public List<BookDto> getCustomerHistory(@PathVariable int id) {
        return customerService.getCustomerHistory(id).stream()
                .map(BookDto::fromEntity)
                .toList();
    }
}
