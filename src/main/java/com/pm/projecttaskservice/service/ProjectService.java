package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.AddMemberRequest;
import com.pm.projecttaskservice.dtos.CreateProjectRequest;
import com.pm.projecttaskservice.dtos.ProjectResponse;
import com.pm.projecttaskservice.models.Project;
import com.pm.projecttaskservice.models.ProjectMember;
import com.pm.projecttaskservice.models.ProjectRole;
import com.pm.projecttaskservice.repo.ProjectMemberRepository;
import com.pm.projecttaskservice.repo.ProjectRepository;
import com.pm.projecttaskservice.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;


    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {
        CurrentUser currentUser = getCurrentUser();
        Project project = new Project(
                request.getName(),
                request.getDescription(),
                currentUser.getUserId()
        );
        Project savedProject = projectRepository.save(project);

        ProjectMember adminMember = new ProjectMember(
                savedProject,
                currentUser.getUserId(),
                ProjectRole.ADMIN
        );
        projectMemberRepository.save(adminMember);

        return mapToProjectResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getMyProjects() {
        CurrentUser currentUser = getCurrentUser();

        return projectRepository.findProjectsByUserId(currentUser.getUserId())
                .stream()
                .map(this::mapToProjectResponse)
                .toList();
    }

    @Override
    public void addMember(Long projectId, AddMemberRequest request) {
        CurrentUser currentUser = getCurrentUser();

        validateAdmin(projectId, currentUser.getUserId());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean alreadyMember = projectMemberRepository.existsByProjectIdAndUserId(
                projectId,
                request.getUserId()
        );

        if (alreadyMember) {
            throw new RuntimeException("User is already a member of this project");
        }

        ProjectMember projectMember = new ProjectMember(
                project,
                request.getUserId(),
                request.getRole()
        );

        projectMemberRepository.save(projectMember);
    }

    @Override
    @Transactional
    public void removeMember(Long projectId, Long userId) {
        CurrentUser currentUser = getCurrentUser();

        validateAdmin(projectId, currentUser.getUserId());

        if (currentUser.getUserId().equals(userId)) {
            throw new RuntimeException("Admin cannot remove himself from the project");
        }

        boolean memberExists = projectMemberRepository.existsByProjectIdAndUserId(
                projectId,
                userId
        );

        if (!memberExists) {
            throw new RuntimeException("User is not a member of this project");
        }

        projectMemberRepository.deleteByProjectIdAndUserId(projectId, userId);

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
    private ProjectResponse mapToProjectResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedByUserId(),
                project.getCreatedAt()
        );
    }
}
