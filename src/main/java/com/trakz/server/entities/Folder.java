package com.trakz.server.entities;

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
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "folder_id", referencedColumnName = "id", nullable = true)
  private List<Task> tasks;
}
