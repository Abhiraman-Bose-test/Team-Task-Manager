package com.pm.projecttaskservice.controllers;

import com.pm.projecttaskservice.dtos.AddMemberRequest;
import com.pm.projecttaskservice.dtos.CreateProjectRequest;
import com.pm.projecttaskservice.dtos.ProjectResponse;
import com.pm.projecttaskservice.service.IProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request
    ) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getMyProjects() {
        return ResponseEntity.ok(projectService.getMyProjects());
    }
    @PostMapping("/{projectId}/members")
    public ResponseEntity<String> addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddMemberRequest request
    ) {
        projectService.addMember(projectId, request);
        return ResponseEntity.ok("Member added successfully");
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<String> removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId
    ) {
        projectService.removeMember(projectId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }
}
