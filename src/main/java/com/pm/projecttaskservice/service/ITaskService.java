package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.CreateTaskRequest;
import com.pm.projecttaskservice.dtos.TaskResponse;
import com.pm.projecttaskservice.dtos.UpdateTaskStatusRequest;

import java.util.List;

public interface ITaskService {

    public TaskResponse createTask(Long projectId, CreateTaskRequest request);
    public List<TaskResponse> getTasksByProject(Long projectId);
    public List<TaskResponse> getMyTasks();
    public TaskResponse updateTaskStatus(Long taskId, UpdateTaskStatusRequest request);
    public void deleteTask(Long taskId);
}
