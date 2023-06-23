package com.trakz.server.services.implementation;

import com.trakz.server.entities.Note;
import com.trakz.server.entities.Task;
import com.trakz.server.repositories.TaskNoteRepository;
import com.trakz.server.repositories.TaskRepository;
import com.trakz.server.services.TaskService;
import com.trakz.server.utils.enumeration.VirtualFolder;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.trakz.server.utils.enumeration.VirtualFolder.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;
  private final TaskNoteRepository taskNoteRepository;

  @Override
  public Task createTask(Task task) {
    log.info("Saving new task {} to the database", task.getContent());

    // add first a note them the task
    Note note = new Note();
    note.setContent(task.getNote().getContent());
    var savedNote = taskNoteRepository.save(note);
    task.setNote(savedNote);

    var saved = taskRepository.save(task);
    saved.setSteps(new ArrayList<>());
    return saved;
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

  @Override
  public Page<Task> getAllTasksByCriteria(@NonNull String virtualFolderName, Pageable pageable) {
    if (!isItAVirtualFolder(virtualFolderName)) {
      return Page.empty();
    }
    if (MY_DAY.equalsName(virtualFolderName)) {
      return taskRepository.findAllByIsInMyDayIsTrue(pageable);
    } else if (IMPORTANT.equalsName(virtualFolderName)) {
      return taskRepository.findAllByIsImportantIsTrue(pageable);
    } else if (PLANNED.equalsName(virtualFolderName)) {
      return taskRepository.findAllByDueDateIsNotNull(pageable);
    }
    return Page.empty();
  }

  public boolean isItAVirtualFolder(String folderName) {
    return VirtualFolder.exists(folderName);
  }
}
