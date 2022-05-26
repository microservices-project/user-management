package com.microservices.user.command;

import com.microservices.user.core.data.User;
import com.microservices.user.core.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreateRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserCreateRepository userCreateRepository;

    @BeforeEach
    public void setUp() {
        userCreateRepository = new UserCreateRepository(userRepository);
    }

    @Test
    void create() {
        // When
        when(userRepository.save(any())).thenReturn(new User());

        // Then
        userCreateRepository.create(new User());

        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void delete() {
        // When
        doNothing().when(userRepository).deleteById(any());

        // Then
        userCreateRepository.delete(1L);

        verify(userRepository, times(1)).deleteById(any());
        verifyNoMoreInteractions(userRepository);
    }
}