package com.microservices.user.query;

import com.microservices.user.core.data.User;
import com.microservices.user.core.exception.DataNotFoundException;
import com.microservices.user.query.mapper.FindUserMapper;
import com.microservices.user.query.rest.FindUserQueryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class UserProjectionTest {

    @Mock
    private  UserFindRepository userFindRepository;

    @Mock
    private  FindUserMapper findUserMapper;


    private UserProjection userProjection;

    @BeforeEach
    public void setUp(){
        this.userProjection = new UserProjection(userFindRepository,findUserMapper);
    }

    @Test
    void handleFindUserShouldReturnUser() {
        // Given
        User user = new User(1L,"first", "last", "email@email.com", "password", "address");
        FindUserQueryModel userMapped = new FindUserQueryModel(1L,"first", "last", "email@email.com", "password", "address");
        // When
        when(userFindRepository.find(1L)).thenReturn(user);
        when(findUserMapper.toModel(user)).thenReturn(userMapped);

        FindUserQueryModel result = userProjection.handleFindUser(1L);

        assertNotNull(result);
        assertEquals(userMapped.getFirstName(), result.getFirstName());
        assertEquals(userMapped.getId(), result.getId());
        assertEquals(userMapped.getLastName(), result.getLastName());
        assertEquals(userMapped.getEmail(), result.getEmail());
        assertEquals(userMapped.getAddress(), result.getAddress());
    }

    @Test
    void handleFindUserShouldThrowUserNotFoundException() {

        when(userFindRepository.find(1L)).thenThrow(new DataNotFoundException("user not found"));

        Throwable exception = assertThrows(DataNotFoundException.class, () -> userProjection.handleFindUser(1L));

        assertEquals("user not found", exception.getMessage());
    }

    @Test
    void handleFindUsersShouldReturnListOfUsers() {
        List<User> users = new ArrayList<>();
        User user = new User(1L , "first", "last", "email@email.com", "password" , "address");
        users.add(user);

        List<FindUserQueryModel> usersMapped = new ArrayList<>();
        FindUserQueryModel userMapped = new FindUserQueryModel(1L,"first", "last", "email@email.com", "password", "address");
        usersMapped.add(userMapped);
        // when
        when(userFindRepository.findAll()).thenReturn(users);
        when(findUserMapper.toModelList(users)).thenReturn(usersMapped);

        List<FindUserQueryModel> results = userProjection.handleFindUsers();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(userMapped.getId() , results.get(0).getId());
        assertEquals(userMapped.getFirstName(), results.get(0).getFirstName());
        assertEquals(userMapped.getLastName(), results.get(0).getLastName());
        assertEquals(userMapped.getEmail(), results.get(0).getEmail());
        assertEquals(userMapped.getPassword(), results.get(0).getPassword());
        assertEquals(userMapped.getAddress(), results.get(0).getAddress());
    }

    @Test
    void handleFindUserByEmailShouldReturnUser() {

        // Given
        User user = new User(1L , "first", "last", "email@email.com", "password" , "address");
        FindUserQueryModel userMapped = new FindUserQueryModel(1L,"first", "last", "email@email.com", "password", "address");

        // When
        when(userFindRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(findUserMapper.toModel(user)).thenReturn(userMapped);
        // Then
        FindUserQueryModel result = userProjection.handleFindUserByEmail(userMapped.getEmail());

        assertNotNull(result);
        assertEquals(user.getId() , result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getAddress(), result.getAddress());
    }

    @Test
    void handleFindUserByEmailShouldThrowUserNotFoundException() {

        when(userFindRepository.findByEmail("")).thenThrow(new DataNotFoundException("user not found"));

        Throwable exception = assertThrows(DataNotFoundException.class, () -> userProjection.handleFindUserByEmail(""));

        assertEquals("user not found", exception.getMessage());
    }
}