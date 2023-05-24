package com.trakz.server.services.implementation;

import com.trakz.server.entities.TaskStep;
import com.trakz.server.repositories.TaskStepRepository;
import com.trakz.server.services.TaskStepService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaskStepServiceImpl implements TaskStepService {
  final private TaskStepRepository taskStepRepository;

  @Override
  public TaskStep createTaskStep(TaskStep taskStep) {
    log.info("Saving new task step {} to the database", taskStep.getContent());
    return taskStepRepository.save(taskStep);
  }

  @Override
  public TaskStep updateTaskStep(TaskStep taskStep) {
    TaskStep taskStepFromDb = taskStepRepository.findById(taskStep.getId()).orElse(null);
    if (taskStepFromDb == null) {
      log.error("Task step with id {} not found", taskStep.getId());
      return null;
    }

    if (taskStep.getContent() != null &&
      !taskStep.getContent().isEmpty() &&
      !taskStep.getContent().isBlank() &&
      !taskStep.getContent().equals(taskStepFromDb.getContent())) {
      taskStepFromDb.setContent(taskStep.getContent());
    }

    if (taskStep.isCompleted() != taskStepFromDb.isCompleted()) {
      taskStepFromDb.setCompleted(taskStep.isCompleted());
    }
    log.info("Updating task step with id {}", taskStep.getId());

    return taskStepRepository.save(taskStepFromDb);
  }

  @Override
  public TaskStep getOneTaskStepById(Long id) {
    log.info("Fetching task step with id {}", id);
    return taskStepRepository.findById(id).orElse(null);
  }

  @Override
  public Page<TaskStep> getTaskStepsByTaskId(Long taskId, Pageable pageable) {
    log.info("Fetching task steps for task with id {}", taskId);
//    return taskStepRepository.findAllByTaskId(taskId, pageable);
    return null;
  }

  @Override
  public Boolean deleteTaskStepById(Long id) {
    log.info("Deleting task step with id {}", id);
    TaskStep taskStep = taskStepRepository.findById(id).orElse(null);
    if (taskStep == null) {
      log.error("Task step with id {} not found", id);
      return false;
    }
    taskStepRepository.deleteById(id);
    return true;
  }
}
