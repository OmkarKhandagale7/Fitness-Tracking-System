package com.coachar.fitness_tracking_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coachar.fitness_tracking_api.exception.ResourceNotFoundException;
import com.coachar.fitness_tracking_api.model.ActivityLog;
import com.coachar.fitness_tracking_api.model.User;
import com.coachar.fitness_tracking_api.repository.ActivityLogRepository;

@Service
public class ActivityLogService {

	@Autowired
    private ActivityLogRepository activityLogRepository;
	
	@Autowired
    private UserService userService; // Dependency to validate User existence


    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    public ActivityLog getActivityLogById(Long id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity Log not found with id: " + id));
    }

    public ActivityLog createActivityLog(Long userId, ActivityLog activityLog) {
        // Ensure the user exists
        User user = userService.getUserById(userId);
        activityLog.setUser(user);
        return activityLogRepository.save(activityLog);
    }

    public ActivityLog updateActivityLog(Long id, ActivityLog updatedActivityLog) {
        ActivityLog existingActivityLog = getActivityLogById(id);
        existingActivityLog.setActivityDate(updatedActivityLog.getActivityDate());
        existingActivityLog.setActivityName(updatedActivityLog.getActivityName());
        existingActivityLog.setCaloriesBurned(updatedActivityLog.getCaloriesBurned());
        return activityLogRepository.save(existingActivityLog);
    }

    public void deleteActivityLog(Long id) {
        ActivityLog activityLog = getActivityLogById(id);
        activityLogRepository.delete(activityLog);
    }
}
