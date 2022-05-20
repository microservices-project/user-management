package com.microservices.user.query.rest;

import com.microservices.user.core.exception.DataNotFoundException;
import com.microservices.user.query.UserProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserQueryControllerTest {
    @MockBean
    private UserProjection userProjection;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getUsers() throws Exception {
        List<FindUserQueryModel> users = new ArrayList<>();
        users.add(new FindUserQueryModel(1L, "first", "last", "email", "address", ""));
        when(userProjection.handleFindUsers()).thenReturn(users);
        mockMvc.perform(get("/user-management/users")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getUser() throws Exception {
        FindUserQueryModel user = new FindUserQueryModel(1L, "first", "last", "email", "address", "");
        when(userProjection.handleFindUser(1L)).thenReturn(user);
        mockMvc.perform(get("/user-management/users/1")).andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void getUserShouldThrowDataNotFoundException() throws Exception {
        when(userProjection.handleFindUser(1L)).thenThrow(new DataNotFoundException("User not found"));
        mockMvc.perform(get("/user-management/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}
