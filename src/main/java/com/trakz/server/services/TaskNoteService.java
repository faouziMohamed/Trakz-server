package com.trakz.server.services;

import com.trakz.server.entities.TaskNote;

public interface TaskNoteService {

  TaskNote updateTaskNoteContent(Long id, TaskNote taskNote);

  TaskNote getTaskNoteById(Long id);

  TaskNote deleteTaskNoteById(Long id);
}
