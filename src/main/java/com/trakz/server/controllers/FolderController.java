package com.trakz.server.controllers;

import com.trakz.server.entities.Folder;
import com.trakz.server.entities.dtos.HttpResponseDto;
import com.trakz.server.entities.dtos.PagedResult;
import com.trakz.server.services.implementation.FolderServiceImpl;
import com.trakz.server.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@RestController
@RequestMapping("/api/v1/folders")
@RequiredArgsConstructor
public class FolderController {
  final private FolderServiceImpl folderService;

  @GetMapping()
  public ResponseEntity<HttpResponseDto> getFolders(Pageable pageable) {
    var pageFolders = folderService.getAllFolders(pageable);
    String message = "Folder list retrieved";
    HttpStatus status = OK;

    if (pageFolders.isEmpty()) {
      message = "No pageFolders found";
      status = NOT_FOUND;
    }
    PagedResult folders = PagedResult.toDtoModel(pageFolders);
    return ResponseEntity.status(status).body(
      HttpUtils.buildResponse(folders, message, status)
    );
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<HttpResponseDto> getFolderById(@PathVariable Long id) {
    var folder = folderService.getFolderById(id);
    String message = "Folder retrieved";
    HttpStatus status = OK;
    if (folder == null) {
      message = "No folder with ID " + id + " found";
      status = NOT_FOUND;
    }
    return ResponseEntity.status(status).body(
      HttpUtils.buildResponse(folder, message, status)
    );
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<HttpResponseDto> getFolderById(@PathVariable String name) {
    Folder folder = folderService.getFolderByName(name);
    String message = "Folder retrieved";
    HttpStatus status = OK;
    if (folder == null) {
      message = "No folder with name " + name + " found";
      status = NOT_FOUND;
    }
    return ResponseEntity.status(status).body(
      HttpUtils.buildResponse(folder, message, status)
    );
  }
}
