package com.trakz.server.repositories;

import com.trakz.server.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findAllByFolderId(Long folderId, Pageable pageable);

  void deleteAllByFolderId(Long folderId);

  Page<Task> findAllByIsImportantIsTrue(Pageable pageable);

  Page<Task> findAllByIsInMyDayIsTrue(Pageable pageable);


  Page<Task> findAllByDueDateIsNotNull(Pageable pageable);
}
