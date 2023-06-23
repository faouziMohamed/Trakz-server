package com.trakz.server.repositories;

import com.trakz.server.entities.TaskStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStepRepository extends JpaRepository<TaskStep, Long> {
  Page<TaskStep> findAllByTaskId(Long taskId, Pageable pageable);

  void deleteByTaskId(Long taskId);
}
