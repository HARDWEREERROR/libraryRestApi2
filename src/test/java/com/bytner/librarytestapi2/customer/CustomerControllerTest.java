package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.book.BookRepository;
import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.TypeOfBook;
import com.bytner.librarytestapi2.customer.model.Customer;
import com.bytner.librarytestapi2.customer.model.command.CreateCustomerCommand;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;


    @BeforeEach
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

        Book book2 = Book.builder()
                .id(2)
                .title("W pust")
                .author("Jezus")
                .typeOfBook(TypeOfBook.ADVENTURE)
                .status(BookStatus.IN_STORAGE)
                .rentAvaliable(true)
                .active(true)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        Customer customer1 = Customer.builder()
                .id(1)
                .firstName("Hubert")
                .lastName("Bytner")
                .adress("Gromadka")
                .active(true)
                .bookSet(List.of(book1, book2))
                .build();

        Customer customer2 = Customer.builder()
                .id(2)
                .firstName("Adam")
                .lastName("Czechowski")
                .adress("Lodz")
                .active(true)
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customer = customer1;
    }

    @Test
    void testSave_SaveCompleted() throws Exception {
        CreateCustomerCommand command = new CreateCustomerCommand();
        command.setFirstName("Hubert");
        command.setLastName("Bytner");
        command.setAdress("Gromadka");

        int expectedId = 3;
        assertFalse(customerRepository.findById(expectedId).isPresent());
        mockMvc.perform(post("/api/LibraryRestAPI2/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.firstName").value(command.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(command.getLastName()))
                .andExpect(jsonPath("$.adress").value(command.getAdress()));

        assertTrue(customerRepository.findById(expectedId).isPresent());
    }

    @Test
    void testGetBooks_ResultsInBooksDtoListReturned() throws Exception {

        int customerId = 1;

        mockMvc.perform(get("/api/LibraryRestAPI2/customers/" + customerId + "/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]", notNullValue()))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].id", equalTo(1)));
    }

    @Test
    void testGetBooks_ResultsInBooksDtoListIsEmpty() throws Exception {

        int customerId = 2;

        mockMvc.perform(get("/api/LibraryRestAPI2/customers/" + customerId + "/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetCustomers_ResultsInCustomerDtoPageReturns() throws Exception {

        mockMvc.perform(get("/api/LibraryRestAPI2/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].id", equalTo(1)));
    }

    @Test
    void testGetCustomers_ResultsInCustomerDtoPageIsEmpty() throws Exception {
        customerRepository.deleteAll();
        mockMvc.perform(get("/api/LibraryRestAPI2/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content", hasSize(0)));

    }

//    @AfterEach
//    void teardown() {
//        customerRepository.deleteAll();
//    }
}
