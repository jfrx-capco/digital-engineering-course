package com.digital.engineering.course.training.integration;

import com.digital.engineering.course.training.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Test
    public void shouldSaveAUser() throws Exception {
        this.mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"firstName\": \"Christy\", \"lastName\": \"DCosta\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        String validUserId = "61571528dd1d00473c7d645b";
        mockMvc.perform(delete("/users/id/"+validUserId))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName", is("Alice")))
            .andExpect(jsonPath("$[1].firstName", is("Bob")));
    }

    @Test
    public void shouldReturnValidUser_IfValidUserId() throws Exception {
        String validUserId = "6155e48c06e03c187d7a3289";
        mockMvc.perform(get("/users/id/"+validUserId)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("Alice")))
            .andExpect(jsonPath("$.lastName", is("Smith")));
    }

    @Test
    public void shouldReturnNotFound_ifInvalidUserId() throws Exception {
        String invalidUserId = "123";
        mockMvc.perform(get("/users/id/"+invalidUserId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
