package com.trakz.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicUpdate
@Table(name = "folders", indexes = {
  @Index(name = "folder_name_index", columnList = "name", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = true)
  private String description;

  // A folder can have many tasks but a task can only belong to one folder
  // This field should be ignored when this entity is being fetched on the task side
//  @JsonIgnoreProperties("folder") // ignore the folder field when serializing this entity
  // include only the name field of the folder when serializing this entity and ignore the rest
  @JsonIgnoreProperties(value = {"folder.tasks", "folder.description"})
  @OneToMany(mappedBy = "folder", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Task> tasks;

  @Override
  public String toString() {
    return "Folder{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + "\"" +
      '}';
  }
}
