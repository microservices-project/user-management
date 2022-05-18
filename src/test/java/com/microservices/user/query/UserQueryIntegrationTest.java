package com.microservices.user.query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class UserQueryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getUsersShouldReturnListOfUsers() throws Exception {
        mockMvc.perform(get("/user-management/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Sql({"classpath:user_data.sql"})
    public void getUserShouldReturnUser() throws Exception {
        mockMvc.perform(get("/user-management/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getUserShouldThrowDataNotFoundException() throws Exception {
        mockMvc.perform(get("/user-management/users/2"))
                .andExpect(status().isNotFound());
    }
}
