package com.pm.projecttaskservice.service;

import com.pm.projecttaskservice.dtos.DashboardResponse;

public interface IDashboardService {

    DashboardResponse getProjectDashboard(Long projectId);
}
