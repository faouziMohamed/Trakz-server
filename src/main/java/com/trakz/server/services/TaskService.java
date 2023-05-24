package com.trakz.server.services;

import com.trakz.server.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
  Task createTask(Task task);

  Task getTaskById(Long taskId);

  Task updateTask(Long id, Task task);

  void deleteTask(Long id);

  Page<Task> getAllTasks(Pageable pageable);

  Page<Task> getAllTasksByFolderId(Long folderId, Pageable pageable);

  Boolean existsById(Long id);

  Boolean setTaskNoteId(Long taskId, Long taskNoteId);
}
