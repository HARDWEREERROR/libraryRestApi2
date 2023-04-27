package com.bytner.librarytestapi2.customer;

import com.bytner.librarytestapi2.bookuser.RentRepository;
import com.bytner.librarytestapi2.bookuser.model.Rent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentRepository rentRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void getAllRentDtos_ReturnsListOfRentDtos() throws Exception {
//        // given
//        List<Rent> rents = Arrays.asList(
//                new Rent(1L, "Jan", "Kowalski", Arrays.asList("English", "German")),
//                new Rent(2L, "Anna", "Nowak", Arrays.asList("Spanish", "French")));
//        given(rentService.getAll()).willReturn(rents);
//
//        // when
//        MvcResult result = mockMvc.perform(get("/rents"))
//                .andExpect(status().isOk())
//                .andReturn();
//        String responseBody = result.getResponse().getContentAsString();
//        List<RentDto> rentDtos = new ObjectMapper().readValue(responseBody, new TypeReference<List<RentDto>>() {});
//
//        // then
//        assertThat(rentDtos).hasSize(2);
//        assertThat(rentDtos.get(0).getId()).isEqualTo(1L);
//        assertThat(rentDtos.get(0).getFirstName()).isEqualTo("Jan");
//        assertThat(rentDtos.get(0).getLastName()).isEqualTo("Kowalski");
//        assertThat(rentDtos.get(0).getLanguages()).containsExactlyInAnyOrder("English", "German");
//        assertThat(rentDtos.get(1).getId()).isEqualTo(2L);
//        assertThat(rentDtos.get(1).getFirstName()).isEqualTo("Anna");
//        assertThat(rentDtos.get(1).getLastName()).isEqualTo("Nowak");
//        assertThat(rentDtos.get(1).getLanguages()).containsExactlyInAnyOrder("Spanish", "French");
//    }
}
