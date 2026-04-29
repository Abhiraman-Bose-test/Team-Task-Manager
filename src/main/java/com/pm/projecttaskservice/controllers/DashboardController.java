package com.pm.projecttaskservice.controllers;

import com.pm.projecttaskservice.dtos.DashboardResponse;
import com.pm.projecttaskservice.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class DashboardController {
    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("/{projectId}/dashboard")
    public ResponseEntity<DashboardResponse> getProjectDashboard(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                dashboardService.getProjectDashboard(projectId)
        );
    }
}
