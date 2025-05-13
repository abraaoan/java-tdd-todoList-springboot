package com.todoList.app.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name="task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "status")
    private boolean completed;

    protected TaskEntity() {}

    public TaskEntity(Integer id, String title, UserEntity user, boolean completed) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isCompleted() {
        return completed;
    }
}
