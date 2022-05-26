package com.microservices.user.command.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.common.command.rest.CreateUserRestModel;
import com.microservices.common.command.rest.LoginRequestRestModel;
import com.microservices.user.command.UserAggregate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserAggregate userAggregate;

    @Test
    public void makeSignInShouldReturnJwt() throws Exception {
        LoginResponseRestModel user = new LoginResponseRestModel("jwt");
        when(userAggregate.handleSignInCommand(any())).thenReturn(user);
        mockMvc.perform(post("/auth-management/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequestRestModel("test.user@gmail.com", "PASSWORD")))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("jwt"));
    }

    @Test
    public void makeUserSignInShouldReturnBadRequestWhenInvalidEmail() throws Exception {
        LoginResponseRestModel user = new LoginResponseRestModel("jwt");
        when(userAggregate.handleSignInCommand(any())).thenReturn(user);
        mockMvc.perform(post("/auth-management/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequestRestModel("test.usermail.com", "PASSWORD")))
                ).andExpect(status().isBadRequest());
    }

    @Test
    public void makeUserSignInShouldReturnBadRequestWhenMissingEmail() throws Exception {
        LoginResponseRestModel user = new LoginResponseRestModel("jwt");
        when(userAggregate.handleSignInCommand(any())).thenReturn(user);
        mockMvc.perform(post("/auth-management/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequestRestModel(null, "PASSWORD")))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void makeUserSignInShouldReturnBadRequestWhenEmptyPassword() throws Exception {
        LoginResponseRestModel user = new LoginResponseRestModel("jwt");
        when(userAggregate.handleSignInCommand(any())).thenReturn(user);
        mockMvc.perform(post("/auth-management/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequestRestModel(null, null)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void makeSignUpShouldReturnSuccess() throws Exception {
        doNothing().when(userAggregate).handleCreateUserCommand(any());
        mockMvc.perform(post("/auth-management/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateUserRestModel("test", "test", "test.user@gmail.com", "PASSWORD", "address")))
        ).andExpect(status().isCreated());
    }

    @Test
    public void makeUserSignUpShouldReturnBadRequestWhenInvalidEmail() throws Exception {
        doNothing().when(userAggregate).handleCreateUserCommand(any());
        mockMvc.perform(post("/auth-management/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateUserRestModel("test", "test", "test.usergmail.com", "PASSWORD", "address")))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void makeUserSignUpShouldReturnBadRequestWhenMissingEmail() throws Exception {
        doNothing().when(userAggregate).handleCreateUserCommand(any());
        mockMvc.perform(post("/auth-management/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateUserRestModel("test", "test", null, "PASSWORD", "address")))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void makeUserSignUpShouldReturnBadRequestWhenEmptyPassword() throws Exception {
        doNothing().when(userAggregate).handleCreateUserCommand(any());
        mockMvc.perform(post("/auth-management/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateUserRestModel("test", "test", null, null, "address")))
        ).andExpect(status().isBadRequest());
    }

}
