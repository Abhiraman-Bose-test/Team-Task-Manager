package com.pm.projecttaskservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "project_members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"project_id", "user_id"})
        }
)
@Setter
@Getter
@NoArgsConstructor
public class ProjectMember extends BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;

    public ProjectMember(Project project, Long userId, ProjectRole role) {
        this.project = project;
        this.userId = userId;
        this.role = role;
    }
}
