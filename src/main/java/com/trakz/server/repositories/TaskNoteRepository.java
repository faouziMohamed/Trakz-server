package com.trakz.server.repositories;

import com.trakz.server.entities.TaskNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskNoteRepository extends JpaRepository<TaskNote, Long> {
}
