package com.coachar.fitness_tracking_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Duration (in weeks) is required")
    private Integer durationInWeeks;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	public WorkoutPlan() {
		// TODO Auto-generated constructor stub
	}
	
	public WorkoutPlan(Long id, @NotBlank(message = "Plan name is required") String planName,
			@NotNull(message = "Duration (in weeks) is required") Integer durationInWeeks, String description,
			User user) {
		super();
		this.id = id;
		this.planName = planName;
		this.durationInWeeks = durationInWeeks;
		this.description = description;
		this.user = user;
	}

	public WorkoutPlan(Long id, @NotBlank(message = "Plan name is required") String planName,
			@NotNull(message = "Duration (in weeks) is required") Integer durationInWeeks, String description) {
		super();
		this.id = id;
		this.planName = planName;
		this.durationInWeeks = durationInWeeks;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getDurationInWeeks() {
		return durationInWeeks;
	}

	public void setDurationInWeeks(Integer durationInWeeks) {
		this.durationInWeeks = durationInWeeks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

