package com.bytner.librarytestapi2.rent;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.bookuser.RentRepository;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.TypeOfBook;
import com.bytner.librarytestapi2.customer.CustomerRepository;
import com.bytner.librarytestapi2.customer.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    void init() {
        Book book1 = Book.builder()
                .id(1)
                .title("W pust")
                .author("Jezus")
                .typeOfBook(TypeOfBook.ADVENTURE)
                .status(BookStatus.IN_STORAGE)
                .rentAvaliable(true)
                .active(true)
                .build();

        bookRepository.save(book1);

        Customer customer1 = Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Gromadka")
                .active(true)
                .build();

        customerRepository.save(customer1);

        Rent rent1 = Rent.builder()
                .localDateFrom(LocalDate.now())
                .localDateTo(LocalDate.now().plusDays(2))
                .book(book1)
                .customer(customer1)
                .build();

        rentRepository.save(rent1);

        Rent rent2 = Rent.builder()
                .localDateFrom(LocalDate.now().plusDays(4))
                .localDateTo(LocalDate.now().plusDays(6))
                .book(book1)
                .customer(customer1)
                .build();

        rentRepository.save(rent2);
    }

    @Test
    void testFindAll_ResultsInRentDtoListReturned() throws Exception {

        mockMvc.perform(get("/api/LibraryRestAPI2/rent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())


        //można sprawdzić kolejne dane

        ;

    }
}
