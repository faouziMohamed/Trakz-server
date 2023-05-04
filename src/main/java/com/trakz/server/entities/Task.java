package com.trakz.server.entities;

import com.trakz.server.utils.enumeration.Recurrence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("DefaultAnnotationParam")
@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // foreign key to folders table
  // a folder can have many tasks but a task can only belong to one folder
  @Column(name = "folder_id", nullable = false)
  private Long folderId;

  @Column(name = "due_date")
  private LocalDateTime dueDate = null;

  @Column(nullable = false)
  private String content;

  @Column(nullable = true)
  private boolean isInMyDay = false;

  @Column(nullable = true)
  private boolean isImportant = false;

  @Column(nullable = true)
  private boolean isCompleted = false;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = true)
  private List<TaskStep> steps;

  @Enumerated(EnumType.STRING)
  @Column(name = "recurrence", nullable = true)
  private Recurrence recurrence;

  @Column(name = "recurrence_every", nullable = true)
  private int recurrenceEvery = 0;

  @Column(name = "recurrence_unit", nullable = true)
  private String recurrenceUnit;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = true)
  private TaskNote taskNote;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
