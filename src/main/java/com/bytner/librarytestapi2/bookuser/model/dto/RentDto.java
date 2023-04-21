package com.bytner.librarytestapi2.bookuser.model.dto;

import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RentDto {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateFrom;
    private LocalDate localDateTo;
    private int bookId;
    private int customerId;

    public static RentDto fromEntity(Rent rent){
        return RentDto.builder()
                .id(rent.getId())
                .localDateFrom(rent.getLocalDateFrom())
                .localDateTo(rent.getLocalDateTo())
                .bookId(rent.getBook().getId())
                .customerId(rent.getCustomer().getId())
                .build();
    }
}
