package com.pm.projecttaskservice.dtos;

import com.pm.projecttaskservice.models.ProjectRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {
    private Long userId;
    private ProjectRole role = ProjectRole.MEMBER;
}
