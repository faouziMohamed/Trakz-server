package com.trakz.server.controllers;

import com.trakz.server.entities.dtos.HttpResponseDto;
import com.trakz.server.entities.dtos.TaskNoteDto;
import com.trakz.server.services.TaskNoteService;
import com.trakz.server.services.TaskService;
import com.trakz.server.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class TaskNoteController {
    final private TaskNoteService taskNoteService;
    private final TaskService taskService;
    private final String dataName = "taskNote";

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponseDto> getTaskNote(@PathVariable Long id) {
        var taskNote = taskNoteService.getTaskNoteById(id);
        if (taskNote == null) {
            return ResponseEntity.status(404).body(
                    HttpUtils.buildResponseWithoutData("TaskNote with ID " + id + " not found", NOT_FOUND)
            );
        }
        return ResponseEntity.ok(
                HttpUtils.buildResponse(dataName, taskNote, "TaskNote retrieved", OK)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponseDto> updateTaskNote(
            @PathVariable Long id,
            @RequestBody TaskNoteDto taskNoteDto
    ) {
        var taskNote = taskNoteDto.toEntity();
        var updatedTaskNote = taskNoteService.updateTaskNoteContent(id, taskNote);
        if (updatedTaskNote == null) {
            return ResponseEntity.status(404).body(
                    HttpUtils.buildResponseWithoutData("TaskNote with ID " + id + " not found", NOT_FOUND)
            );
        }
        return ResponseEntity.ok(
                HttpUtils.buildResponse(dataName, updatedTaskNote, "TaskNote updated", OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponseDto> deleteTaskNote(
            @PathVariable Long id
    ) {
        var deletedTaskNote = taskNoteService.deleteTaskNoteById(id);
        if (deletedTaskNote == null) {
            return ResponseEntity.status(404).body(
                    HttpUtils.buildResponseWithoutData("Cannot delete a note that does not exist, ID: " + id, NOT_FOUND)
            );
        }
        return ResponseEntity.ok(
                HttpUtils.buildResponse(dataName, deletedTaskNote, "TaskNote deleted", OK)
        );
    }
}
