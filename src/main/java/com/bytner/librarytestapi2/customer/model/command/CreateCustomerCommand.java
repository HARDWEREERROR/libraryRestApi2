package com.bytner.librarytestapi2.customer.model.command;

import com.bytner.librarytestapi2.customer.model.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCustomerCommand {

    @NotBlank(message = "firstName cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,19}", message = "firstName has to match the pattern [A-Z][a-z]{1,19}")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,29}", message = "lastName has to match the pattern {regexp}")
    private String lastName;

    @NotBlank
    private String adress;

    public Customer toEntity() {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .adress(adress)
                .active(true)
                .build();

    }
}
