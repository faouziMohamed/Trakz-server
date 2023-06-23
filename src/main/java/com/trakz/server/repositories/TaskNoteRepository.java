package com.trakz.server.repositories;

import com.trakz.server.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskNoteRepository extends JpaRepository<Note, Long> {
}
