package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.CreateTaskRequest;
import com.pm.projecttaskservice.dtos.TaskResponse;
import com.pm.projecttaskservice.dtos.UpdateTaskStatusRequest;
import com.pm.projecttaskservice.models.*;
import com.pm.projecttaskservice.repo.ProjectMemberRepository;
import com.pm.projecttaskservice.repo.ProjectRepository;
import com.pm.projecttaskservice.repo.TaskRepository;
import com.pm.projecttaskservice.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Override
    @Transactional
    public TaskResponse createTask(Long projectId, CreateTaskRequest request) {
        CurrentUser currentUser = getCurrentUser();

        validateAdmin(projectId, currentUser.getUserId());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean assignedUserIsMember = projectMemberRepository.existsByProjectIdAndUserId(
                projectId,
                request.getAssignedToUserId()
        );

        if (!assignedUserIsMember) {
            throw new RuntimeException("Assigned user is not a member of this project");
        }

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                request.getPriority(),
                TaskStatus.TODO,
                project,
                request.getAssignedToUserId(),
                currentUser.getUserId()
        );

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getTasksByProject(Long projectId) {
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

        return tasks.stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getMyTasks() {
        CurrentUser currentUser = getCurrentUser();

        return taskRepository.findByAssignedToUserId(currentUser.getUserId())
                .stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, UpdateTaskStatusRequest request) {
        CurrentUser currentUser = getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Long projectId = task.getProject().getId();

        ProjectMember currentMember = projectMemberRepository
                .findByProjectIdAndUserId(projectId, currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("You are not a member of this project"));

        boolean isAdmin = currentMember.getRole() == ProjectRole.ADMIN;
        boolean isAssignedUser = task.getAssignedToUserId().equals(currentUser.getUserId());

        if (!isAdmin && !isAssignedUser) {
            throw new RuntimeException("You can update only your assigned tasks");
        }

        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        return mapToTaskResponse(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        CurrentUser currentUser = getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Long projectId = task.getProject().getId();

        validateAdmin(projectId, currentUser.getUserId());

        taskRepository.delete(task);

    }

    private void validateAdmin(Long projectId, Long userId) {
        boolean isAdmin = projectMemberRepository.existsByProjectIdAndUserIdAndRole(
                projectId,
                userId,
                ProjectRole.ADMIN
        );

        if (!isAdmin) {
            throw new RuntimeException("Only project admin can perform this action");
        }
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

    private TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus(),
                task.getProject().getId(),
                task.getAssignedToUserId(),
                task.getCreatedByUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
