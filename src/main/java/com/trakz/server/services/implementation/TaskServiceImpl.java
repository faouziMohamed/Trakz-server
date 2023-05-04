package com.trakz.server.services.implementation;

import com.trakz.server.entities.Task;
import com.trakz.server.repositories.TaskRepository;
import com.trakz.server.services.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;

  @Override
  public Task createTask(Task task) {
    log.info("Saving new task {} to the database", task.getContent());
    return taskRepository.save(task);
  }

  @Override
  public Task getTaskById(Long taskId) {
    log.info("Fetching task with ID {}", taskId);
    return taskRepository.findById(taskId).orElse(null);
  }

  @Override
  public Task updateTask(Long id, Task task) {
    log.info("Updating task with ID {}", id);
    var taskToUpdate = taskRepository.findById(id).orElse(null);
    if (taskToUpdate == null) return null;
    
    if (task.getDueDate() != null && !task.getDueDate().equals(taskToUpdate.getDueDate())) {
      taskToUpdate.setDueDate(task.getDueDate());
    }
    if (task.getFolderId() != null && !task.getFolderId().equals(taskToUpdate.getFolderId())) {
      taskToUpdate.setFolderId(task.getFolderId());
    }
    if (task.getContent() != null && !task.getContent().isEmpty() && !task.getContent().isBlank() && !task.getContent().equals(taskToUpdate.getContent())) {
      taskToUpdate.setContent(task.getContent());
    }
    if (task.isInMyDay() != taskToUpdate.isInMyDay()) {
      taskToUpdate.setInMyDay(task.isInMyDay());
    }
    if (task.isImportant() != taskToUpdate.isImportant()) {
      taskToUpdate.setImportant(task.isImportant());
    }
    if (task.isCompleted() != taskToUpdate.isCompleted()) {
      taskToUpdate.setCompleted(task.isCompleted());
    }
    if (task.getRecurrence() != null) {
      taskToUpdate.setRecurrence(task.getRecurrence());
    } else {
      if (task.getRecurrenceEvery() > 0 && task.getRecurrenceUnit() != null) {
        taskToUpdate.setRecurrence(null);
        taskToUpdate.setRecurrenceEvery(task.getRecurrenceEvery());
        taskToUpdate.setRecurrenceUnit(task.getRecurrenceUnit());
      }
    }
    return taskRepository.save(taskToUpdate);
  }

  @Override
  public void deleteTask(Long id) {
    log.info("Deleting task with ID {}", id);
    taskRepository.deleteById(id);
  }

  @Override
  public Page<Task> getAllTasks(Pageable pageable) {
    log.info("Fetching all tasks from the database");
    return taskRepository.findAll(pageable);
  }

  @Override
  public Page<Task> getAllTasksByFolderId(Long folderId, Pageable pageable) {
    log.info("Fetching all tasks from the database");
    return taskRepository.findAllByFolderId(folderId, pageable);
  }

  @Override
  public Boolean existsById(Long id) {
    return taskRepository.existsById(id);
  }

  @Override
  public Boolean setTaskNoteId(Long taskId, Long taskNoteId) {
    var task = taskRepository.findById(taskId).orElse(null);
    if (task == null) {
      return false;
    }
//    task.setNote(taskNoteId);
    taskRepository.save(task);
    return true;
  }
}
