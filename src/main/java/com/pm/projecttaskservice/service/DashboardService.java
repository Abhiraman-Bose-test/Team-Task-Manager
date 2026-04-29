package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.DashboardResponse;
import com.pm.projecttaskservice.dtos.UserTaskCountResponse;
import com.pm.projecttaskservice.models.ProjectMember;
import com.pm.projecttaskservice.models.ProjectRole;
import com.pm.projecttaskservice.models.Task;
import com.pm.projecttaskservice.models.TaskStatus;
import com.pm.projecttaskservice.repo.ProjectMemberRepository;
import com.pm.projecttaskservice.repo.TaskRepository;
import com.pm.projecttaskservice.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService implements IDashboardService{
    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public DashboardResponse getProjectDashboard(Long projectId) {
        CurrentUser currentUser = getCurrentUser();

        ProjectMember currentMember = projectMemberRepository
                .findByProjectIdAndUserId(projectId, currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("You are not a member of this project"));

        List<Task> tasks;

        if (currentMember.getRole() == ProjectRole.ADMIN) {
            tasks = taskRepository.findByProjectId(projectId);
        } else {
            tasks = taskRepository.findByProjectIdAndAssignedToUserId(
                    projectId,
                    currentUser.getUserId()
            );
        }

        Long totalTasks = (long) tasks.size();

        Map<TaskStatus, Long> tasksByStatus = tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getStatus,
                        Collectors.counting()
                ));

        List<UserTaskCountResponse> tasksPerUser = tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getAssignedToUserId,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new UserTaskCountResponse(entry.getKey(), entry.getValue()))
                .toList();
        Long overdueTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDateTime.now()))
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .count();

        return new DashboardResponse(
                projectId,
                totalTasks,
                tasksByStatus,
                tasksPerUser,
                overdueTasks
        );
    }

    private CurrentUser getCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!(principal instanceof CurrentUser)) {
            throw new RuntimeException("Unauthorized user");
        }

        return (CurrentUser) principal;
    }
}
