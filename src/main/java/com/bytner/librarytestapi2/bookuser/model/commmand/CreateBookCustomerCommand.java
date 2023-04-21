package com.bytner.librarytestapi2.bookuser.model.commmand;

import com.bytner.librarytestapi2.bookuser.model.BookCustomer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateBookCustomerCommand {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDate localDate;

    private int bookId;

    private int customerId;

//    public BookCustomer toEntity(){
//        return BookCustomer.builder()
//                .localDate(localDate)
//                .
//    }
}
