package com.pm.projecttaskservice.controllers;

import com.pm.projecttaskservice.dtos.CreateTaskRequest;
import com.pm.projecttaskservice.dtos.TaskResponse;
import com.pm.projecttaskservice.dtos.UpdateTaskStatusRequest;
import com.pm.projecttaskservice.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request
    ) {
        return ResponseEntity.ok(taskService.createTask(projectId, request));
    }

    @GetMapping("/api/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/api/tasks/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks());
    }

    @PatchMapping("/api/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, request));
    }

    @DeleteMapping("/api/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
