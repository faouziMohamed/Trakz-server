package com.trakz.server.controllers;

import com.trakz.server.entities.TaskStep;
import com.trakz.server.entities.dtos.HttpResponseDto;
import com.trakz.server.entities.dtos.PagedResult;
import com.trakz.server.entities.dtos.TaskStepDto;
import com.trakz.server.services.TaskStepService;
import com.trakz.server.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Service
@RestController
@RequestMapping("/api/v1/steps")
@RequiredArgsConstructor
public class TaskStepController {
  final private TaskStepService taskStepService;

  @GetMapping("/{id}")
  public ResponseEntity<HttpResponseDto> getTaskNote(@PathVariable Long id) {
    var taskNote = taskStepService.getOneTaskStepById(id);
    if (taskNote == null) {
      return ResponseEntity.status(404).body(
        HttpUtils.buildResponseWithoutData("No task step with ID " + id + " found", NOT_FOUND)
      );
    }
    String dataName = "taskStep";
    return ResponseEntity.ok(
      HttpUtils.buildResponse(dataName, taskNote, "TaskNote retrieved", OK)
    );
  }

  @GetMapping("/task/{taskId}")
  public ResponseEntity<HttpResponseDto> getTaskNotesByTaskId(@PathVariable Long taskId, Pageable pageable) {
    Page<TaskStep> fetchedTaskSteps = taskStepService.getTaskStepsByTaskId(taskId, pageable);
    PagedResult stepsDto = PagedResult.toDtoModel(fetchedTaskSteps);

    String message = "TaskSteps retrieved";
    HttpStatus status = OK;
    if (fetchedTaskSteps.getContent().isEmpty()) {
      message = "No steps found for task with ID " + taskId;
      status = NOT_FOUND;
    }
    return ResponseEntity.status(status).body(
      HttpUtils.buildResponse(stepsDto, message, status)
    );
  }

  @PostMapping
  public ResponseEntity<HttpResponseDto> createTaskStep(
    @RequestBody TaskStepDto taskStepDto
  ) {
    if (taskStepDto.getTaskId() == null || taskStepDto.getTaskId() <= 0) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("Task ID must be provided and be greater than 0", BAD_REQUEST)
      );
    }
    if (taskStepDto.getContent() == null || taskStepDto.getContent().isEmpty() || taskStepDto.getContent().isBlank()) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("TaskStep content must be provided", BAD_REQUEST)
      );
    }
  
    TaskStep taskStep = taskStepDto.toEntity();
    TaskStep newTaskStep = taskStepService.createTaskStep(taskStep);
    return ResponseEntity.ok(
      HttpUtils.buildResponse(newTaskStep, "TaskStep created", OK)
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<HttpResponseDto> updateTaskStep(
    @PathVariable Long id,
    @RequestBody TaskStepDto taskStepDto
  ) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("TaskStep ID must be provided and be greater than 0", BAD_REQUEST)
      );
    }

    if (taskStepDto.getContent() == null || taskStepDto.getContent().isEmpty() || taskStepDto.getContent().isBlank()) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("TaskStep content is required", BAD_REQUEST)
      );
    }

    TaskStep taskStep = taskStepDto.toEntity();
    taskStep.setId(id);
    TaskStep updatedTaskStep = taskStepService.updateTaskStep(taskStep);
    if (updatedTaskStep == null) {
      return ResponseEntity.status(404).body(
        HttpUtils.buildResponseWithoutData("TaskStep with ID " + id + " not found", NOT_FOUND)
      );
    }
    return ResponseEntity.ok(
      HttpUtils.buildResponse(updatedTaskStep, "TaskStep updated", OK)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpResponseDto> deleteTaskStep(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("TaskStep ID must be provided and be greater than 0", BAD_REQUEST)
      );
    }

    Boolean isDeleted = taskStepService.deleteTaskStepById(id);
    if (!isDeleted) {
      return ResponseEntity.status(404).body(
        HttpUtils.buildResponseWithoutData("Cannot delete TaskStep with ID " + id + " because it does not exist", NOT_FOUND)
      );
    }
    return ResponseEntity.ok(
      HttpUtils.buildResponseWithoutData("TaskStep with ID " + id + " deleted", OK)
    );
  }
}
