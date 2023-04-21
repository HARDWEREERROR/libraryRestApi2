package com.bytner.librarytestapi2.bookuser;

import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.bookuser.model.commmand.CreateRentCommand;
import com.bytner.librarytestapi2.bookuser.model.dto.RentDto;
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
@RequestMapping("api/LibraryRestAPI2/rent")
public class RentController {

    private final RentService rentService;

    @GetMapping
    public List<RentDto> getAll() {
        return rentService.getAll().stream()
                .map(RentDto::fromEntity)
                .toList();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RentDto createBookCustomer(@RequestBody @Valid CreateRentCommand createBookCustomerCommand) {
        Rent toSave = createBookCustomerCommand.toEntity();
        Rent saved = rentService.save(toSave, createBookCustomerCommand.getBookId(), createBookCustomerCommand.getCustomerId());
        return RentDto.fromEntity(saved);
    }
}
