package com.coachar.fitness_tracking_api.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.coachar.fitness_tracking_api.exception.ResourceNotFoundException;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        user = new User(1L, "Aarav Kumar", "aarav@example.com", "password123");
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Aarav Kumar", users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> users = userService.getAllUsers();

        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Aarav Kumar", foundUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(2L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("Aarav Kumar", createdUser.getName());
        assertEquals("aarav@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setName("Aryan Kumar");
        user.setEmail("aryan@example.com");

        User updatedUser = userService.updateUser(1L, user);

        assertNotNull(updatedUser);
        assertEquals("Aryan Kumar", updatedUser.getName());
        assertEquals("aryan@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(2L, user);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        doNothing().when(userRepository).delete(any(User.class));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(2L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }
}
