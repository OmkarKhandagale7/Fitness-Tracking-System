package com.coachar.fitness_tracking_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coachar.fitness_tracking_api.model.ActivityLog;
import com.coachar.fitness_tracking_api.service.ActivityLogService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @Operation(summary = "Get all activity logs", description = "Retrieve a list of all activity logs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of activity logs", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityLog.class)))
    })
    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllActivityLogs() {
        return ResponseEntity.ok(activityLogService.getAllActivityLogs());
    }

    @Operation(summary = "Get activity log by ID", description = "Retrieve a specific activity log by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the activity log", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityLog.class))),
        @ApiResponse(responseCode = "404", description = "Activity log not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        return ResponseEntity.ok(activityLogService.getActivityLogById(id));
    }

    @Operation(summary = "Create a new activity log", description = "Create a new activity log for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the activity log", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityLog.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<ActivityLog> createActivityLog(
            @PathVariable Long userId,
            @Valid @RequestBody ActivityLog activityLog) {
        ActivityLog createdLog = activityLogService.createActivityLog(userId, activityLog);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an activity log", description = "Update an existing activity log by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the activity log", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityLog.class))),
        @ApiResponse(responseCode = "404", description = "Activity log not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivityLog(
            @PathVariable Long id,
            @Valid @RequestBody ActivityLog activityLog) {
        return ResponseEntity.ok(activityLogService.updateActivityLog(id, activityLog));
    }

    @Operation(summary = "Delete an activity log", description = "Delete an activity log by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the activity log"),
        @ApiResponse(responseCode = "404", description = "Activity log not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        activityLogService.deleteActivityLog(id);
        return ResponseEntity.noContent().build();
    }
}
