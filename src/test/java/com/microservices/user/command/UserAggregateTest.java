package com.microservices.user.command;

import com.microservices.common.command.rest.CreateUserRestModel;
import com.microservices.common.command.rest.LoginRequestRestModel;
import com.microservices.common.config.security.JwtTokenUtil;
import com.microservices.user.command.mapper.CreateUserMapper;
import com.microservices.user.command.producer.UserProducer;

import com.microservices.user.command.rest.LoginResponseRestModel;

import com.microservices.user.core.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAggregateTest {

    @Mock
    private UserCreateRepository userCreateRepository;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserProducer userProducer;

    private UserAggregate userAggregate;

    @BeforeEach
    public void setUp() {
        this.userAggregate =  new UserAggregate(userCreateRepository, createUserMapper, authenticationManager, jwtTokenUtil,passwordEncoder, userProducer);
    }

    @Test
    public void handleCreateUserCommandOK() {

        // Given
        CreateUserRestModel user = new CreateUserRestModel("first", "last", "email@email.com", "password", "address");
        // When
        when(passwordEncoder.encode(user.getPassword())).thenReturn("");
        when(createUserMapper.toEntity(user)).thenReturn(new User());
        doNothing().when(userCreateRepository).create(any());
        // Then
        userAggregate.handleCreateUserCommand(user);
        verify(userCreateRepository,times(1)).create(any());
    }

   @Test
    public void handleDeleteUserCommandOK() {
        // when
       doNothing().when(userCreateRepository).delete(any());
       // Then
       userAggregate.handleDeleteUserCommand(1L);
       verify(userCreateRepository,times(1)).delete(any());

   }

    @Test
    public void makeHandleSignInCommandReturnJWT() {
        // Given
        LoginRequestRestModel login = new LoginRequestRestModel("email@email.com", "password");
        // When
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        when(jwtTokenUtil.generateToken(login)).thenReturn("jwt");
        // Then
        LoginResponseRestModel result = userAggregate.handleSignInCommand(login);

        verify(authenticationManager,times(1)).authenticate(any());
        verify(jwtTokenUtil,times(1)).generateToken(any());

        assertEquals(result.getJwt(), "jwt");

    }

}