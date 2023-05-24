package com.trakz.server.services.implementation;

import com.trakz.server.entities.TaskNote;
import com.trakz.server.repositories.TaskNoteRepository;
import com.trakz.server.services.TaskNoteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaskNoteServiceImpl implements TaskNoteService {
  final private TaskNoteRepository taskNoteRepository;

  @Override
  public TaskNote updateTaskNoteContent(Long id, TaskNote taskNote) {
    String content = taskNote.getNote();
    TaskNote taskNoteToUpdate = taskNoteRepository.findById(id).orElse(null);
    if (taskNoteToUpdate == null) return null;
    if (content != null && !content.equals(taskNoteToUpdate.getNote())) {
      taskNoteToUpdate.setNote(content);
    }
    log.info("Updating task note with id {}", taskNoteToUpdate.getId());
    return taskNoteRepository.save(taskNoteToUpdate);
  }

  @Override
  public TaskNote getTaskNoteById(Long id) {
    log.info("Fetching task note with id {}", id);
    return taskNoteRepository.findById(id).orElse(null);
  }

  @Override
  public TaskNote deleteTaskNoteById(Long id) {
    log.info("Deleting task note with id {}", id);
    TaskNote taskNote = taskNoteRepository.findById(id).orElse(null);
    if (taskNote == null) return null;
    taskNoteRepository.deleteById(id);
    return taskNote;
  }
}
