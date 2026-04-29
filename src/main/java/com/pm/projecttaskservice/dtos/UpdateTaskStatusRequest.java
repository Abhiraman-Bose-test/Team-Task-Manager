package com.pm.projecttaskservice.dtos;

import com.pm.projecttaskservice.models.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTaskStatusRequest {
    private TaskStatus status;
    public UpdateTaskStatusRequest() {
    }

    public TaskStatus getStatus() {
        return status;
    }
}
