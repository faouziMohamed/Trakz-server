package com.trakz.server.services;

import com.trakz.server.entities.TaskStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskStepService {
  TaskStep createTaskStep(TaskStep taskStep);

  TaskStep updateTaskStep(TaskStep taskStep);

  TaskStep getOneTaskStepById(Long id);

  Page<TaskStep> getTaskStepsByTaskId(Long taskId, Pageable pageable);

  Boolean deleteTaskStepById(Long id);

  void deleteAllByTaskId(Long taskId);

}
