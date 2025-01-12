package com.coachar.fitness_tracking_api.service;
import com.coachar.fitness_tracking_api.exception.ResourceNotFoundException;
import com.coachar.fitness_tracking_api.model.ActivityLog;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.repository.ActivityLogRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ActivityLogServiceTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ActivityLogService activityLogService;

    private ActivityLog activityLog;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");

        activityLog = new ActivityLog();
        activityLog.setId(1L);
        activityLog.setActivityName("Running");
        LocalDate date = LocalDate.parse("2025-01-12");

        activityLog.setActivityDate(date);
        activityLog.setCaloriesBurned(500);
        activityLog.setUser(user);
    }

    @Test
    void testGetAllActivityLogs() {
        when(activityLogRepository.findAll()).thenReturn(Arrays.asList(activityLog));

        assertEquals(1, activityLogService.getAllActivityLogs().size());
        verify(activityLogRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityLogById_ActivityLogExists() {
        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(activityLog));

        ActivityLog foundLog = activityLogService.getActivityLogById(1L);
        assertNotNull(foundLog);
        assertEquals("Running", foundLog.getActivityName());
        verify(activityLogRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivityLogById_ActivityLogNotFound() {
        when(activityLogRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            activityLogService.getActivityLogById(1L);
        });
        assertEquals("Activity Log not found with id: 1", exception.getMessage());
        verify(activityLogRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateActivityLog() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(activityLogRepository.save(activityLog)).thenReturn(activityLog);

        ActivityLog createdLog = activityLogService.createActivityLog(1L, activityLog);

        assertNotNull(createdLog);
        assertEquals(1L, createdLog.getUser().getId());
        verify(userService, times(1)).getUserById(1L);
        verify(activityLogRepository, times(1)).save(activityLog);
    }

    @Test
    void testUpdateActivityLog() {
        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(activityLog));
        ActivityLog updatedLog = new ActivityLog();
        updatedLog.setActivityName("Swimming");
        LocalDate date = LocalDate.parse("2025-01-12");

        activityLog.setActivityDate(date);
        updatedLog.setCaloriesBurned(400);

        when(activityLogRepository.save(any(ActivityLog.class))).thenReturn(updatedLog);

        ActivityLog result = activityLogService.updateActivityLog(1L, updatedLog);
        
        assertEquals("Swimming", result.getActivityName());
        assertEquals("2025-01-13", result.getActivityDate());
        assertEquals(400, result.getCaloriesBurned());
        verify(activityLogRepository, times(1)).findById(1L);
        verify(activityLogRepository, times(1)).save(any(ActivityLog.class));
    }

    @Test
    void testDeleteActivityLog() {
        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(activityLog));

        activityLogService.deleteActivityLog(1L);

        verify(activityLogRepository, times(1)).findById(1L);
        verify(activityLogRepository, times(1)).delete(activityLog);
    }

    @Test
    void testDeleteActivityLog_ActivityLogNotFound() {
        when(activityLogRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            activityLogService.deleteActivityLog(1L);
        });
        assertEquals("Activity Log not found with id: 1", exception.getMessage());
        verify(activityLogRepository, times(1)).findById(1L);
    }
}
