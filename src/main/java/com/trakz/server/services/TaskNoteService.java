package com.trakz.server.services;

import com.trakz.server.entities.Note;

public interface TaskNoteService {

  Note updateTaskNoteContent(Long id, Note note);

  Note getTaskNoteById(Long id);

  Note deleteTaskNoteById(Long id);
}
