package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.AddMemberRequest;
import com.pm.projecttaskservice.dtos.CreateProjectRequest;
import com.pm.projecttaskservice.dtos.ProjectResponse;

import java.util.List;

public interface IProjectService {
    public ProjectResponse createProject(CreateProjectRequest request);
    public List<ProjectResponse> getMyProjects();
    public void addMember(Long projectId, AddMemberRequest request);
    public void removeMember(Long projectId, Long userId);
}
