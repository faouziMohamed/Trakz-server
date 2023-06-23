package com.trakz.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.ArrayList;
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

  // a folder can have many tasks but a task can only belong to one folder
  @Column(name = "folder_id", nullable = false)
  private Long folderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "folder_id", insertable = false, updatable = false)
  @JsonIgnore
  private Folder folder;

  @Column(name = "due_date")
  private LocalDateTime dueDate = null;

  @Column(nullable = false)
  private String content;

  @Column(nullable = true)
  @JsonProperty("isInMyDay")
  private boolean isInMyDay = false;

  @Column(nullable = true)
  @JsonProperty("isImportant")
  private boolean isImportant = false;

  @Column(nullable = true)
  @JsonProperty("isCompleted")
  private boolean isCompleted = false;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
  private List<TaskStep> steps = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "recurrence", nullable = true)
  private Recurrence recurrence;

  @Column(name = "recurrence_every", nullable = true)
  private int recurrenceEvery = 0;

  @Column(name = "recurrence_unit", nullable = true)
  private String recurrenceUnit;

  @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = true)
  private Note note;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @JsonProperty("folderName")
  public String getFolderName() {
    return folder != null ? folder.getName() : null;
  }
}
