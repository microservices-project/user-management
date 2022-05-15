package com.microservices.user.command.rest;

import com.microservices.user.command.UserAggregate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/user_data.sql"})
public class UserCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAggregate userAggregate;

    @Test
    @WithMockUser("test.user@gmail.com")
    public void deleteUser_OK() throws Exception {
        doNothing().when(userAggregate).handleDeleteUserCommand(any());
        mockMvc.perform(delete("/user-management/users/1"))
                .andExpect(status().isNoContent());
    }
}
