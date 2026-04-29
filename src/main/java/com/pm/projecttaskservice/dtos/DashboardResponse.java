package com.pm.projecttaskservice.dtos;

import com.pm.projecttaskservice.models.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DashboardResponse {
    private Long projectId;

    private Long totalTasks;

    private Map<TaskStatus, Long> tasksByStatus;

    private List<UserTaskCountResponse> tasksPerUser;

    private Long overdueTasks;
}
