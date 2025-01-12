package com.coachar.fitness_tracking_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coachar.fitness_tracking_api.model.WorkoutPlan;
import com.coachar.fitness_tracking_api.service.WorkoutPlanService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Operation(summary = "Get all workout plans", description = "Retrieve a list of all workout plans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of workout plans retrieved successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutPlan.class)))
    })
    @GetMapping
    public ResponseEntity<List<WorkoutPlan>> getAllWorkoutPlans() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlans());
    }

    @Operation(summary = "Get workout plan by ID", description = "Retrieve a specific workout plan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout plan retrieved successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutPlan.class))),
        @ApiResponse(responseCode = "404", description = "Workout plan not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getWorkoutPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById(id));
    }

    @Operation(summary = "Create a new workout plan", description = "Create a new workout plan for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Workout plan created successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutPlan.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<WorkoutPlan> createWorkoutPlan(
            @PathVariable Long userId,
            @Valid @RequestBody WorkoutPlan workoutPlan) {
        WorkoutPlan createdPlan = workoutPlanService.createWorkoutPlan(userId, workoutPlan);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing workout plan", description = "Update the details of an existing workout plan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout plan updated successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutPlan.class))),
        @ApiResponse(responseCode = "404", description = "Workout plan not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlan> updateWorkoutPlan(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutPlan workoutPlan) {
        return ResponseEntity.ok(workoutPlanService.updateWorkoutPlan(id, workoutPlan));
    }

    @Operation(summary = "Delete a workout plan", description = "Delete a specific workout plan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Workout plan deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Workout plan not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable Long id) {
        workoutPlanService.deleteWorkoutPlan(id);
        return ResponseEntity.noContent().build();
    }
}
