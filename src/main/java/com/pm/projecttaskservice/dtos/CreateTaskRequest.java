package com.pm.projecttaskservice.dtos;

import com.pm.projecttaskservice.models.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class CreateTaskRequest {
    private String title;
    private String description;
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDateTime dueDate;
    @NotNull(message = "Priority is required")
    private TaskPriority priority;
    @NotNull(message = "Assigned user id is required")
    private Long assignedToUserId;

}
