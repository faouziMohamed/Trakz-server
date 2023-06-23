package com.trakz.server.services.implementation;

import com.trakz.server.entities.Note;
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
  public Note updateTaskNoteContent(Long id, Note note) {
    String content = note.getContent();
    Note noteToUpdate = taskNoteRepository.findById(id).orElse(null);
    if (noteToUpdate == null) return null;
    if (content != null && !content.equals(noteToUpdate.getContent())) {
      noteToUpdate.setContent(content);
    }
    log.info("Updating task note with id {}", noteToUpdate.getId());
    return taskNoteRepository.save(noteToUpdate);
  }

  @Override
  public Note getTaskNoteById(Long id) {
    log.info("Fetching task note with id {}", id);
    return taskNoteRepository.findById(id).orElse(null);
  }

  @Override
  public Note deleteTaskNoteById(Long id) {
    log.info("Deleting task note with id {}", id);
    Note note = taskNoteRepository.findById(id).orElse(null);
    if (note == null) return null;
    taskNoteRepository.deleteById(id);
    return note;
  }
}
