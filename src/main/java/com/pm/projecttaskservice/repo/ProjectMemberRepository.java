package com.pm.projecttaskservice.repo;

import com.pm.projecttaskservice.models.ProjectMember;
import com.pm.projecttaskservice.models.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {
    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);

    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

    boolean existsByProjectIdAndUserIdAndRole(Long projectId, Long userId, ProjectRole role);

    List<ProjectMember> findByProjectId(Long projectId);

    void deleteByProjectIdAndUserId(Long projectId, Long userId);
}
