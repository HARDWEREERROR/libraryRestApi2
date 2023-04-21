package com.bytner.librarytestapi2.bookuser.model.commmand;

import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRentCommand {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateTo;

    private int bookId;

    private int customerId;

    public Rent toEntity(){
        return Rent.builder()
                .localDateFrom(localDateFrom)
                .localDateTo(localDateTo)
                .build();
    }
}
