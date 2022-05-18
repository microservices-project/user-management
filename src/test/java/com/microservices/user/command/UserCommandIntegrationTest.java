package com.microservices.user.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class UserCommandIntegrationTest {
    public static final String USER_MANAGEMENT_URL = "/user-management";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @Sql({"classpath:user_data.sql"})
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(USER_MANAGEMENT_URL + "/users/1")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void deleteUserShouldReturnNotFoundException() throws Exception {
        mockMvc.perform(delete(USER_MANAGEMENT_URL + "/users/2")).andExpect(status().isNotFound());
    }

}
