package com.coachar.fitness_tracking_api.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.coachar.fitness_tracking_api.exception.ResourceNotFoundException;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.model.WorkoutPlan;
import com.coachar.fitness_tracking_api.repository.WorkoutPlanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutPlanServiceTest {

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private WorkoutPlanService workoutPlanService;

    private User user;
    private WorkoutPlan workoutPlan;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        user = new User(1L, "John Doe", "john@example.com", "password123");
        workoutPlan = new WorkoutPlan(1L, "Full Body Plan", 12, "A full-body workout plan", user);
    }

    @Test
    public void testGetAllWorkoutPlans() {
        when(workoutPlanRepository.findAll()).thenReturn(List.of(workoutPlan));

        List<WorkoutPlan> workoutPlans = workoutPlanService.getAllWorkoutPlans();

        assertEquals(1, workoutPlans.size());
        assertEquals("Full Body Plan", workoutPlans.get(0).getPlanName());
    }

    @Test
    public void testGetWorkoutPlanById() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.of(workoutPlan));

        WorkoutPlan foundPlan = workoutPlanService.getWorkoutPlanById(1L);

        assertNotNull(foundPlan);
        assertEquals("Full Body Plan", foundPlan.getPlanName());
    }

    @Test
    public void testGetWorkoutPlanById_NotFound() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            workoutPlanService.getWorkoutPlanById(2L);
        });

        assertEquals("Workout Plan not found with id: 2", exception.getMessage());
    }

    @Test
    public void testCreateWorkoutPlan() {
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(workoutPlanRepository.save(any(WorkoutPlan.class))).thenReturn(workoutPlan);

        WorkoutPlan createdPlan = workoutPlanService.createWorkoutPlan(1L, workoutPlan);

        assertNotNull(createdPlan);
        assertEquals("Full Body Plan", createdPlan.getPlanName());
        assertEquals(user, createdPlan.getUser());
    }

    @Test
    public void testUpdateWorkoutPlan() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.of(workoutPlan));
        when(workoutPlanRepository.save(any(WorkoutPlan.class))).thenReturn(workoutPlan);

        workoutPlan.setPlanName("Updated Full Body Plan");
        workoutPlan.setDurationInWeeks(16);

        WorkoutPlan updatedPlan = workoutPlanService.updateWorkoutPlan(1L, workoutPlan);

        assertNotNull(updatedPlan);
        assertEquals("Updated Full Body Plan", updatedPlan.getPlanName());
        assertEquals(16, updatedPlan.getDurationInWeeks());
    }

    @Test
    public void testUpdateWorkoutPlan_NotFound() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            workoutPlanService.updateWorkoutPlan(2L, workoutPlan);
        });

        assertEquals("Workout Plan not found with id: 2", exception.getMessage());
    }

    @Test
    public void testDeleteWorkoutPlan() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.of(workoutPlan));
        doNothing().when(workoutPlanRepository).delete(any(WorkoutPlan.class));

        workoutPlanService.deleteWorkoutPlan(1L);

        verify(workoutPlanRepository, times(1)).delete(workoutPlan);
    }

    @Test
    public void testDeleteWorkoutPlan_NotFound() {
        when(workoutPlanRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            workoutPlanService.deleteWorkoutPlan(2L);
        });

        assertEquals("Workout Plan not found with id: 2", exception.getMessage());
    }
}

