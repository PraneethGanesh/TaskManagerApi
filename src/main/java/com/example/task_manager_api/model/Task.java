package com.example.task_manager_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private boolean completed;

}
