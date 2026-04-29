package com.pm.projecttaskservice.repo;

import com.pm.projecttaskservice.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.userId = :userId")
    List<Project> findProjectsByUserId(Long userId);
}
