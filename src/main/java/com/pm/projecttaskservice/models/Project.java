package com.pm.projecttaskservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Setter
@Getter
@NoArgsConstructor
public class Project extends BaseModel{
    private String name;
    private String description;
    private Long createdByUserId;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> members=new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks=new ArrayList<>();

    public Project(String name, String description, Long createdByUserId) {
        this.name = name;
        this.description = description;
        this.createdByUserId = createdByUserId;
    }
}
