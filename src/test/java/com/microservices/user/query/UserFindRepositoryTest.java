package com.microservices.user.query;

import com.microservices.user.core.data.User;
import com.microservices.user.core.data.UserRepository;
import com.microservices.user.core.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFindRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserFindRepository userFindRepository;

    @BeforeEach
    public void setUp(){
        userFindRepository = new UserFindRepository(userRepository);
    }

    @Test
    void getUserByIdShouldReturnUser() {
        // Given
        User user = new User(1L , "first", "last", "email@email.com", "password" , "address");
        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Then
        User result = userFindRepository.find(1L);

        assertNotNull(result);
        assertEquals(user.getId() , result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getAddress(), result.getAddress());
    }
    @Test
    void getUserByIdShouldThrowDataNotFoundException() {

        // When
        when(userRepository.findById(1L)).thenThrow(new DataNotFoundException("user not found"));

        Throwable exception = assertThrows(DataNotFoundException.class, () -> userFindRepository.find(1L));

        assertEquals("user not found", exception.getMessage());

    }
    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        User user = new User(1L , "first", "last", "email@email.com", "password" , "address");
        users.add(user);
        // when
        when(userRepository.findAll()).thenReturn(users);

        List<User> results = userFindRepository.findAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(user.getId() , results.get(0).getId());
        assertEquals(user.getFirstName(), results.get(0).getFirstName());
        assertEquals(user.getLastName(), results.get(0).getLastName());
        assertEquals(user.getEmail(), results.get(0).getEmail());
        assertEquals(user.getPassword(), results.get(0).getPassword());
        assertEquals(user.getAddress(), results.get(0).getAddress());

    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByEmailShouldReturnUser() {
        // Given
        User user = new User(1L , "first", "last", "email@email.com", "password" , "address");
        // When
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Then
        User result = userFindRepository.findByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getId() , result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getAddress(), result.getAddress());
    }
    @Test
    void findByEmailShouldThrowDataNotFoundException() {

        // When
        when(userRepository.findUserByEmail(anyString())).thenThrow(new DataNotFoundException("user not found"));

        // Then
        Throwable exception = assertThrows(DataNotFoundException.class, () -> userFindRepository.findByEmail("email@email.com"));

        assertEquals("user not found", exception.getMessage());

    }
}