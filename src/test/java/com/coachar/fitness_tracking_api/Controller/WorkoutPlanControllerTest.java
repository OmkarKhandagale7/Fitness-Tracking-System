package com.coachar.fitness_tracking_api.Controller;
import com.coachar.fitness_tracking_api.controller.WorkoutPlanController;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.model.WorkoutPlan;
import com.coachar.fitness_tracking_api.service.WorkoutPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WorkoutPlanControllerTest {

    @Mock
    private WorkoutPlanService workoutPlanService;

    @InjectMocks
    private WorkoutPlanController workoutPlanController;

    private MockMvc mockMvc;

    private WorkoutPlan workoutPlan;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workoutPlanController).build();
        User user = new User(1L, "Aarav Kumar", "aarav@example.com", "password123");
        workoutPlan = new WorkoutPlan(1L, "Full Body Workout", 4, "A full-body workout plan", user);    }

    @Test
    public void testGetAllWorkoutPlans() throws Exception {
        List<WorkoutPlan> workoutPlans = Arrays.asList(workoutPlan);

        when(workoutPlanService.getAllWorkoutPlans()).thenReturn(workoutPlans);

        mockMvc.perform(get("/api/workout-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Full Body Workout"));
    }

    @Test
    public void testGetWorkoutPlanById() throws Exception {
        when(workoutPlanService.getWorkoutPlanById(anyLong())).thenReturn(workoutPlan);

        mockMvc.perform(get("/api/workout-plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Full Body Workout"));
    }

    @Test
    public void testCreateWorkoutPlan() throws Exception {
        when(workoutPlanService.createWorkoutPlan(anyLong(), any(WorkoutPlan.class))).thenReturn(workoutPlan);

        mockMvc.perform(post("/api/workout-plans/user/1")
                .contentType("application/json")
                .content("{\"name\":\"Full Body Workout\",\"description\":\"A full-body workout plan\",\"duration\":30}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Full Body Workout"))
                .andExpect(jsonPath("$.description").value("A full-body workout plan"))
                .andExpect(jsonPath("$.duration").value(30));
    }

    @Test
    public void testUpdateWorkoutPlan() throws Exception {
    	
    	User user = new User(1L, "Aarav Kumar", "aarav@example.com", "password123");

    	WorkoutPlan updatedWorkoutPlan = new WorkoutPlan(1L, "Updated Full Body Workout", 45, "An updated full-body workout plan", user);

        when(workoutPlanService.updateWorkoutPlan(anyLong(), any(WorkoutPlan.class))).thenReturn(updatedWorkoutPlan);

        mockMvc.perform(put("/api/workout-plans/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated Full Body Workout\",\"description\":\"An updated full-body workout plan\",\"duration\":45}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Full Body Workout"))
                .andExpect(jsonPath("$.description").value("An updated full-body workout plan"))
                .andExpect(jsonPath("$.duration").value(45));
    }

    @Test
    public void testDeleteWorkoutPlan() throws Exception {
        doNothing().when(workoutPlanService).deleteWorkoutPlan(anyLong());

        mockMvc.perform(delete("/api/workout-plans/1"))
                .andExpect(status().isNoContent());
    }
}

