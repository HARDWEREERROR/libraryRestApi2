package com.bytner.librarytestapi2.book;

import com.bytner.librarytestapi2.book.model.Book;
import com.bytner.librarytestapi2.book.model.command.CreateBookCommand;
import com.bytner.librarytestapi2.common.BookStatus;
import com.bytner.librarytestapi2.common.TypeOfBook;
import com.bytner.librarytestapi2.customer.CustomerRepository;
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
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

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
        book = book1;
    }

    @Test
    void testSave_SaveCompleted() throws Exception {
        CreateBookCommand command = new CreateBookCommand();
        command.setTitle("Aaaaa");
        command.setAuthor("Bytner Hubert");
        command.setTypeOfBook(TypeOfBook.ADVENTURE);

        int expectedId = 3;
        assertFalse(bookRepository.findById(expectedId).isPresent());
        mockMvc.perform(post("/api/LibraryRestAPI2/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.firstName").value(command.getTitle()))
                .andExpect(jsonPath("$.lastName").value(command.getAuthor()))
                .andExpect(jsonPath("$.adress").value(command.getTypeOfBook()));

        assertTrue(bookRepository.findById(expectedId).isPresent());
    }

    @Test
    void testGetBooks_ResultsInCustomerDtoPageReturns() throws Exception {

        mockMvc.perform(get("/api/LibraryRestAPI2/books"))
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
    void testGetBooks_ResultsInCustomerDtoPageIsEmpty() throws Exception {
        bookRepository.deleteAll();
        mockMvc.perform(get("/api/LibraryRestAPI2/books"))
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
