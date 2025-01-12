package com.coachar.fitness_tracking_api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    // Testing ResourceNotFoundException
    @Test
    public void testHandleResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/api/users/999")) // Assume user with ID 999 does not exist
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    // Testing MethodArgumentNotValidException (Validation Error)
    @Test
    public void testHandleValidationExceptions() throws Exception {
        String invalidUserJson = "{ \"name\": \"\", \"email\": \"invalid-email\" }"; // Invalid input data

        mockMvc.perform(post("/api/users") // Assuming this endpoint handles user creation
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Plan name is required"))
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }

    // Testing generic Exception (unexpected errors)
    @Test
    public void testHandleGenericException() throws Exception {
        // You could invoke any API that is likely to throw an unhandled exception
        // For now, we're simulating this by calling a non-existing endpoint
        mockMvc.perform(get("/api/non-existing-endpoint"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred"));
    }
}
