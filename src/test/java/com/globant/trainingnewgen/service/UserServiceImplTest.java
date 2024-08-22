package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.exception.ResourceNotFoundException;
import com.globant.trainingnewgen.mapper.UserMapper;
import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.model.UserDto;
import com.globant.trainingnewgen.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John Doe");

        userDto = new UserDto("John Doe");
    }

    @Test
    void testCreateUser() {


        mockStatic(UserMapper.class);
        when(UserMapper.dtoToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.create(userDto);

        verify(userRepository).save(user);

    }

    @Test
    void testGetUserByName() {
        when(userRepository.findByName(anyString())).thenReturn(Optional.of(user));

        var userSaved = userService.getUserByName("John Doe");

        verify(userRepository).findByName(anyString());
        assertEquals(userSaved.name(), userDto.name());

    }

    @Test
    void testGetUserByNameThrowsException() {
        when(userRepository.findByName(anyString())).thenThrow(new ResourceNotFoundException("User not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserByName("John Doe");
        });

        verify(userRepository).findByName(anyString());

    }



}