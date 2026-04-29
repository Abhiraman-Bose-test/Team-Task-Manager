package com.pm.projecttaskservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserTaskCountResponse {
    private Long userId;
    private Long taskCount;
}
