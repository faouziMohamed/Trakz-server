package com.trakz.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Builder
@Table(name = "tasks_step")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class TaskStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonProperty("isCompleted")
    private boolean isCompleted = false;

    @Column(name = "task_id", nullable = false)
    private Long taskId;
}
