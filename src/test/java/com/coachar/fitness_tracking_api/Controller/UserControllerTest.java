package com.coachar.fitness_tracking_api.Controller;

import com.coachar.fitness_tracking_api.controller.UserController;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        User user1 = new User(1L, "Rajesh Kumar", "rajesh.kumar@example.com", "password123");
        User user2 = new User(2L, "Priya Sharma", "priya.sharma@example.com", "password456");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Rajesh Kumar"))
                .andExpect(jsonPath("$[1].name").value("Priya Sharma"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        User user = new User(1L, "Rajesh Kumar", "rajesh.kumar@example.com", "password123");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rajesh Kumar"))
                .andExpect(jsonPath("$.email").value("rajesh.kumar@example.com"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(999L)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));

        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    void testCreateUser_Success() throws Exception {
        User user = new User(1L, "Rajesh Kumar", "rajesh.kumar@example.com", "password123");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Rajesh Kumar\",\"email\":\"rajesh.kumar@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rajesh Kumar"))
                .andExpect(jsonPath("$.email").value("rajesh.kumar@example.com"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testCreateUser_ValidationFailure() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"email\":\"invalid-email\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        User updatedUser = new User(1L, "Rajesh Kumar", "rajesh.kumar@example.com", "newpassword123");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Rajesh Kumar\",\"email\":\"rajesh.kumar@example.com\",\"password\":\"newpassword123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rajesh Kumar"))
                .andExpect(jsonPath("$.email").value("rajesh.kumar@example.com"));

        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.updateUser(eq(999L), any(User.class))).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Rajesh Kumar\",\"email\":\"rajesh.kumar@example.com\",\"password\":\"newpassword123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));

        verify(userService, times(1)).updateUser(eq(999L), any(User.class));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(999L);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));

        verify(userService, times(1)).deleteUser(999L);
    }
}
