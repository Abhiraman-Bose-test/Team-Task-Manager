package com.pm.projecttaskservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {
    private String name;
    private String description;
}
