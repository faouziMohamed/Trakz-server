package com.trakz.server.controllers;

import com.trakz.server.entities.Folder;
import com.trakz.server.entities.Task;
import com.trakz.server.entities.dtos.CreateTaskRequestDto;
import com.trakz.server.entities.dtos.HttpResponseDto;
import com.trakz.server.entities.dtos.PagedResult;
import com.trakz.server.entities.dtos.UpdateTaskRequestDto;
import com.trakz.server.services.implementation.FolderServiceImpl;
import com.trakz.server.services.implementation.TaskServiceImpl;
import com.trakz.server.utils.HttpUtils;
import com.trakz.server.utils.Utils;
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
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
  final private TaskServiceImpl taskService;
  private final FolderServiceImpl folderService;

  @GetMapping()
  public ResponseEntity<HttpResponseDto> getTasks(
    Pageable pageable,
    @RequestParam(required = false) Long folderId,
    @RequestParam(required = false) String folderName
  ) {
    Page<Task> tasksPaged;
    String message = "Task list retrieved";
    HttpStatus status = OK;
    if (folderName != null) {
      // if it is a virtual folder, filter by criteria
      if (taskService.isItAVirtualFolder(folderName)) {
        tasksPaged = taskService.getAllTasksByCriteria(folderName, pageable);
        PagedResult tasks = PagedResult.toDtoModel(tasksPaged);
        return ResponseEntity.ok(
          HttpUtils.buildResponse(tasks, message, status)
        );
      }
      Folder folder = folderService.getFolderByName(folderName);
      if (folder == null) {
        message = "No folder with name " + folderName + " found";
        status = NOT_FOUND;
        return ResponseEntity.status(status).body(
          HttpUtils.buildResponseWithoutData(message, status)
        );
      }
      folderId = folder.getId();

    }
    tasksPaged = folderId != null ? taskService.getAllTasksByFolderId(folderId, pageable)
      : taskService.getAllTasks(pageable);
    PagedResult tasks = PagedResult.toDtoModel(tasksPaged);
    return ResponseEntity.ok(
      HttpUtils.buildResponse(tasks, "Task list retrieved", OK)
    );
  }

  @PostMapping()
  public ResponseEntity<HttpResponseDto> createTask(@RequestBody CreateTaskRequestDto createTaskDto) {
    if (isCreateTaskReqInValid(createTaskDto)) {
      return ResponseEntity.status(BAD_REQUEST).body(
        HttpUtils.buildResponseWithoutData("The Task content and folder ID or name are required", BAD_REQUEST)
      );
    }

    // Coming here means we have either folder ID or folder name or both
    if (createTaskDto.getFolderId() <= 0) {
      var folder = folderService.getFolderByName(createTaskDto.getFolderName());
      if (folder == null) {
        return ResponseEntity.status(BAD_REQUEST).body(
          HttpUtils.buildResponseWithoutData("The folder name is invalid", BAD_REQUEST)
        );
      }
      createTaskDto.setFolderId(folder.getId());
    } else if (folderService.getFolderById(createTaskDto.getFolderId()) == null) {
      return ResponseEntity.status(BAD_REQUEST).body(
        HttpUtils.buildResponseWithoutData("The folder ID is invalid", BAD_REQUEST)
      );
    }

    var createdTask = taskService.createTask(createTaskDto.toEntity());
    Task taskSaved = taskService.getTaskById(createdTask.getId());
    return ResponseEntity.status(CREATED).body(
      HttpUtils.buildResponse(taskSaved, "New Task created", CREATED)
    );
  }

  private static boolean isCreateTaskReqInValid(CreateTaskRequestDto task) {
    return Utils.isNullOrEmpty(task.getContent()) || (
      task.getFolderId() <= 0 &&
        Utils.isNullOrEmpty(task.getFolderName())
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<HttpResponseDto> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequestDto task) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(400).body(
        HttpUtils.buildResponseWithoutData("The Task ID is required, and must be greater than 0", BAD_REQUEST)
      );
    }
    System.out.println("\n\nTask: " + task + "\n\n");
    Task updatedTask = taskService.updateTask(id, task.toEntity());
    if (updatedTask == null) {
      var message = "Task with ID " + id + " not found";
      return ResponseEntity.status(404).body(
        HttpUtils.buildResponseWithoutData(message, NOT_FOUND)
      );
    }
    return ResponseEntity.ok(
      HttpUtils.buildResponse(updatedTask, "Task updated", OK)
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
