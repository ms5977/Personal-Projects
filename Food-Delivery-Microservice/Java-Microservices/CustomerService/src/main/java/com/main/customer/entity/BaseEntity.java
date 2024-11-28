package com.main.customer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();

    }
    @PreUpdate
    protected  void onUpdate()
    {
        this.updateAt=LocalDateTime.now();
    }
}
