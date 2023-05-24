package com.trakz.server.controllers;

import com.trakz.server.entities.Task;
import com.trakz.server.entities.dtos.CreateTaskRequestDto;
import com.trakz.server.entities.dtos.HttpResponseDto;
import com.trakz.server.entities.dtos.PagedResult;
import com.trakz.server.entities.dtos.UpdateTaskRequestDto;
import com.trakz.server.services.implementation.TaskServiceImpl;
import com.trakz.server.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  final private TaskServiceImpl taskService;
  final private String dataName = "task";

  @GetMapping()
  public ResponseEntity<HttpResponseDto> getTasks(
    Pageable pageable,
    @RequestParam(required = false) Long byFolderId
  ) {
    Page<Task> tasksPaged = byFolderId != null
      ? taskService.getAllTasksByFolderId(byFolderId, pageable)
      : taskService.getAllTasks(pageable);
    PagedResult tasks = PagedResult.toDtoModel(tasksPaged);
    System.out.println(tasks);
    return ResponseEntity.ok(
      HttpUtils.buildResponse(tasks, "Task list retrieved", OK)
    );
  }

  @PostMapping()
  public ResponseEntity<HttpResponseDto> createTask(@RequestBody CreateTaskRequestDto task) {
    if (task.getContent() == null || task.getContent().isEmpty() || task.getContent().isBlank()) {
      return ResponseEntity.status(BAD_REQUEST).body(
        HttpUtils.buildResponseWithoutData("The Task content is required", BAD_REQUEST)
      );
    }
    var createdTask = taskService.createTask(task.toEntity());
    return ResponseEntity.status(CREATED).body(
      HttpUtils.buildResponse(dataName, createdTask, "New Task created", CREATED)
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<HttpResponseDto> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequestDto task) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("The Task ID is required, and must be greater than 0", BAD_REQUEST)
      );
    }
    var updatedTask = taskService.updateTask(id, task.toEntity());
    if (updatedTask == null) {
      var message = "Task with ID " + id + " not found";
      return ResponseEntity.status(404).body(
        HttpUtils.buildResponseWithoutData(message, NOT_FOUND)
      );
    }
    return ResponseEntity.ok(
      HttpUtils.buildResponse(dataName, updatedTask, "Task updated", OK)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpResponseDto> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok(
      HttpUtils.buildResponseWithoutData("Task deleted", OK)
    );
  }

}
