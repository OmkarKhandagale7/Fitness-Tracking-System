package com.coachar.fitness_tracking_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coachar.fitness_tracking_api.exception.ResourceNotFoundException;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.model.WorkoutPlan;
import com.coachar.fitness_tracking_api.repository.WorkoutPlanRepository;

@Service
public class WorkoutPlanService {

	@Autowired
    private WorkoutPlanRepository workoutPlanRepository;
	
	@Autowired
    private UserService userService; // Dependency to validate User existence


    public List<WorkoutPlan> getAllWorkoutPlans() {
        return workoutPlanRepository.findAll();
    }

    public WorkoutPlan getWorkoutPlanById(Long id) {
        return workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Plan not found with id: " + id));
    }

    public WorkoutPlan createWorkoutPlan(Long userId, WorkoutPlan workoutPlan) {
        // Ensure the user exists
        User user = userService.getUserById(userId);
        workoutPlan.setUser(user);
        return workoutPlanRepository.save(workoutPlan);
    }

    public WorkoutPlan updateWorkoutPlan(Long id, WorkoutPlan updatedWorkoutPlan) {
        WorkoutPlan existingWorkoutPlan = getWorkoutPlanById(id);
        existingWorkoutPlan.setPlanName(updatedWorkoutPlan.getPlanName());
        existingWorkoutPlan.setDurationInWeeks(updatedWorkoutPlan.getDurationInWeeks());
        existingWorkoutPlan.setDescription(updatedWorkoutPlan.getDescription());
        return workoutPlanRepository.save(existingWorkoutPlan);
    }

    public void deleteWorkoutPlan(Long id) {
        WorkoutPlan workoutPlan = getWorkoutPlanById(id);
        workoutPlanRepository.delete(workoutPlan);
    }
}

